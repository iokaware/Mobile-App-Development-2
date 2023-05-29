package com.example.firebasechatapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firebasechatapp.R;
import com.example.firebasechatapp.databinding.ActivitySettingsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private ActivitySettingsBinding binding;

    private FirebaseAuth mAuth;

    public static final String PROFILE_PHOTO_BASE_URL =
            "https://firebasestorage.googleapis.com/v0/b/fir-chatapp-f2b3b.appspot.com/o/profile_photos%2F";
    public static final String PROFILE_PHOTO_URL_EXTRA =
            ".jpg?alt=media&token=9fcc3e29-21d7-48dc-bda8-78ce66f1f311";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();

        getUserProfile();

        Glide.with(this)
                .load(PROFILE_PHOTO_BASE_URL
                        + mAuth.getUid()
                        + PROFILE_PHOTO_URL_EXTRA)
                .placeholder(R.drawable.account_icon)
                .error(R.drawable.account_icon)
                .into(binding.profileImage);

        binding.emailVerificationButton.setOnClickListener(view -> {
            sendEmailVerification();
        });

        binding.signOutButton.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(),
                    SignInActivity.class));
            finish();
        });
    }

    public void getUserProfile() {
        // [START get_user_profile]
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
//            Uri photoUrl = user.getPhotoUrl();

            binding.userName.setText(name);
            binding.userEmail.setText(email);

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            binding.emailVerificationButton.setEnabled(!emailVerified);


            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
        // [END get_user_profile]
    }

    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, task -> {
                    // Email sent
                    Log.d(TAG, "Email sent.");
                    mAuth.signOut();
                    Toast.makeText(getApplicationContext(),
                            "Email sent. " +
                                    "Verify email and sign in again!",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),
                            SignInActivity.class));
                    finish();
                });
        // [END send_email_verification]
    }
}