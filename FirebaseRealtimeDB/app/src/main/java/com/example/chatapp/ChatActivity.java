package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ChatActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef, messagesRef;

    private EditText inputMessage;
    private Button buttonSend;
    private TextView textMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        inputMessage = findViewById(R.id.inputMessage);
        buttonSend = findViewById(R.id.buttonSend);
        textMessage = findViewById(R.id.textMessage);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        messagesRef = database.getReference("messages");

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String message = snapshot.getValue(String.class);
//                textMessage.setText(message);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        messagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot mSnapShot : snapshot.getChildren()) {
                    Message message = mSnapShot.getValue(Message.class);
                    textMessage.setText(message.getText());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buttonSend.setOnClickListener(view -> {
//            myRef.setValue(inputMessage.getText().toString());

            Message message = new Message(
                    "",
                    FirebaseAuth.getInstance().getUid(),
                    inputMessage.getText().toString()
            );

            String key = messagesRef.push().getKey();
            messagesRef.child(key).setValue(message);
        });
    }
}