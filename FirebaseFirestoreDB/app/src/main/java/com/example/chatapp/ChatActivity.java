package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends AppCompatActivity {

    private FirebaseDatabase database;

    private DatabaseReference myRef, messagesRef;

    private EditText inputMessage;
    private Button buttonSend;
    private TextView textMessage;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        db = FirebaseFirestore.getInstance();

        inputMessage = findViewById(R.id.inputMessage);
        buttonSend = findViewById(R.id.buttonSend);
        textMessage = findViewById(R.id.textMessage);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
        messagesRef = database.getReference("messages");

        getUsers();

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

        textMessage.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
        });
    }

    public void getUsers() {
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                User user = document.toObject(User.class);
//                                String name = document.get("name", String.class);
//                                String name = (String) document.get("name");
                                Toast.makeText(getApplicationContext(), user.getEmail(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}