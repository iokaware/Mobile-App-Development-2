package com.example.firebasechatapp.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.example.firebasechatapp.databinding.ActivityCreateAccountBinding;
import com.example.firebasechatapp.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateAccountActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private ActivityCreateAccountBinding binding;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseStorage storage;

    private Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        storage = FirebaseStorage.getInstance();

        binding.createAccountButton.setEnabled(false);

        textWatcher();

        binding.createAccountButton.setOnClickListener(view -> {
            String email = binding.emailInput.getText().toString().trim();
            String password = binding.passwordInput.getText().toString().trim();

            if (isNetworkConnected()) {
                createAccount(email, password);
            } else {
                Snackbar.make(view, "Check Network Connection!",
                        Snackbar.LENGTH_SHORT).show();
            }
        });

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            imageUri = result.getData().getData();
                            binding.profileImage.setImageURI(imageUri);
                        }
                    }
                }
        );

        binding.imageAddPhoto.setOnClickListener(view -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            resultLauncher.launch(galleryIntent);
        });
    }

    private void createAccount(String email, String password) {
        String name = binding.nameInput.getText().toString().trim();
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        addUser(new User(
                                mAuth.getCurrentUser().getUid(),
                                name, email, ""
                        ));
                        updateProfile(name);
//                        FirebaseUser user = mAuth.getCurrentUser();
//                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
//                        updateUI(null);
                    }
                });
        // [END create_user_with_email]
    }

    public void addUser(User user) {
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        if (imageUri != null) {
                            uploadImage();
                        } else {
                            startActivity(new Intent(getApplicationContext(),
                                    MainActivity.class));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void uploadImage() {
        StorageReference storageRef  = storage.getReference();
        String fileName = FirebaseAuth.getInstance().getUid() + ".jpg";
        StorageReference profilePhotoRef = storageRef.child("profile_photos/" +
                fileName);

        profilePhotoRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // The image upload is successful.
                        startActivity(new Intent(getApplicationContext(),
                                MainActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the image upload failure
                        Toast.makeText(getApplicationContext(), "Image uploaded failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void updateProfile(String name) {
        // [START update_profile]
        FirebaseUser user = mAuth.getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
//                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "User profile updated.");
//                        startActivity(new Intent(getApplicationContext(),
//                                MainActivity.class));
                    } else {
                        Log.w(TAG, "User profile updated:failure", task.getException());
//                        Toast.makeText(getApplicationContext(), "Authentication failed.",
//                                Toast.LENGTH_SHORT).show();
                    }
                });
        // [END update_profile]
    }

    public void textWatcher() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = binding.nameInput.getText().toString().trim();
                String email = binding.emailInput.getText().toString().trim();
                String password = binding.passwordInput.getText().toString().trim();

                binding.createAccountButton.setEnabled(
                        name.matches("^[a-zA-Z' ]+$")
                        && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                                && password.length() >= 6
                );
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        binding.nameInput.addTextChangedListener(watcher);
        binding.emailInput.addTextChangedListener(watcher);
        binding.passwordInput.addTextChangedListener(watcher);
    }

    public boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }
}