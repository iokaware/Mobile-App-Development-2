package com.example.firebasechatapp.activity;

import static com.example.firebasechatapp.activity.SettingsActivity.PROFILE_PHOTO_BASE_URL;
import static com.example.firebasechatapp.activity.SettingsActivity.PROFILE_PHOTO_URL_EXTRA;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.example.firebasechatapp.model.Message;
import com.example.firebasechatapp.adapter.MessageAdapter;
import com.example.firebasechatapp.R;
import com.example.firebasechatapp.databinding.ActivityChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";

    private ActivityChatBinding binding;

    private FirebaseAuth mAuth;
    private String userId;
    private String receiverId;
    private String receiverName;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private MessageAdapter mAdapter;
    private List<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        // Enable the home button
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

// Set the navigation icon
//        binding.toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        receiverId = getIntent().getStringExtra("receiver_id");
        receiverName = getIntent().getStringExtra("receiver_name");

        binding.textName.setText(receiverName);

        Glide.with(this)
                .load(PROFILE_PHOTO_BASE_URL
                        + receiverId
                        + PROFILE_PHOTO_URL_EXTRA)
                .placeholder(R.drawable.account_icon)
                .error(R.drawable.account_icon)
                .into(binding.imageProfile);

        database = FirebaseDatabase.getInstance();

//        enablePersistence();

        myRef = database.getReference("messages");

        messages = new ArrayList<>();

        mAdapter = new MessageAdapter(getApplicationContext(),
                userId, messages, (position, messageId) -> {
            removeMessage(position, messageId);
        });

        getMessageList();

        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(
                getApplicationContext()
        ));

        binding.buttonSend.setEnabled(false);
        textWatcher();

        binding.buttonSend.setOnClickListener(view -> {
//            writeMessage(binding.messageInput.getText().toString().trim());
//            readMessage();
            writeNewMessage(binding.messageInput.getText().toString().trim());
            binding.messageInput.setText("");
        });

        binding.imageBack.setOnClickListener(view -> finish());
    }

    public void writeMessage(String text) {
        // [START write_message]
        // Write a message to the database
        myRef.setValue(text);
        // [END write_message]
    }

    public void readMessage() {
        // [START read_message]
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
//                binding.messageText.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        // [END read_message]
    }

    private void writeNewMessage(String text) {
        // Create new message at /messages/$messageId
        String messageId = myRef.push().getKey();
        Message message = new Message(messageId, userId,
                receiverId,
                userId+"_"+receiverId,
                text,
                receiverName,
                getCurrentTime());
        myRef.child(userId).child(receiverId).child(messageId).setValue(message);
        myRef.child(receiverId).child(userId).child(messageId).setValue(message);
        // [END write_fan_out]
    }

    private void getMessageList() {
        // [START post_value_event_listener]
        myRef
                .child(userId)
                .child(receiverId)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Message object and use the values to update the UI
                messages.clear();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    Message message = messageSnapshot.getValue(Message.class);
                    messages.add(message);
                }

                // notify the adapter of the data set change
                mAdapter.notifyDataSetChanged();
                binding.recyclerView.scrollToPosition(mAdapter.getItemCount()-1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Message failed, log a message
                Log.w(TAG, "loadMessage:onCancelled", databaseError.toException());
            }
        });
        // [END post_value_event_listener]
    }

    private void removeMessage(int position, String messageId) {
        myRef.child(messageId).removeValue();
        messages.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    private void enablePersistence() {
        // [START rtdb_enable_persistence]
        database.setPersistenceEnabled(true);
        // [END rtdb_enable_persistence]
    }

    public String getCurrentTime() {
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        return timeFormat.format(currentTimeMillis);
    }

    public interface MessageItemListener {
        void onItemLongClickListener(int position, String messageId);
    }

    public void textWatcher() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = binding.messageInput.getText().toString().trim();

                binding.buttonSend.setEnabled(
                        text.length() > 0
                );
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        binding.messageInput.addTextChangedListener(watcher);
    }
}