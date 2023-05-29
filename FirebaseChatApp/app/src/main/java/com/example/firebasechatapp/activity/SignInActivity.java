package com.example.firebasechatapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.example.firebasechatapp.databinding.ActivitySignInBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";

    private ActivitySignInBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        binding.signInButton.setEnabled(false);

        textWatcher();

        binding.signInButton.setOnClickListener(view -> {
            String email = binding.emailInput.getText().toString().trim();
            String password = binding.passwordInput.getText().toString().trim();

            if (isNetworkConnected()) {
                signIn(email, password);
            } else {
                Snackbar.make(view, "Check Network Connection!",
                        Snackbar.LENGTH_SHORT).show();
            }
        });

        binding.createAccount.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), CreateAccountActivity.class));
        });

        binding.forgotPassword.setOnClickListener(view -> {
//            sendPasswordReset("user@example.com");
        });
    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
//                        FirebaseUser user = mAuth.getCurrentUser();
                        startActivity(new Intent(getApplicationContext(),
                                MainActivity.class));
//                            updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                    }
                });
        // [END sign_in_with_email]
    }

    public void sendPasswordReset(String email) {
        // [START send_password_reset]
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Email sent.");
                        Toast.makeText(getApplicationContext(),
                                "Email sent.", Toast.LENGTH_SHORT).show();
                    }
                });
        // [END send_password_reset]
    }

    public void textWatcher() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email = binding.emailInput.getText().toString().trim();
                String password = binding.passwordInput.getText().toString().trim();

                binding.signInButton.setEnabled(
                        Patterns.EMAIL_ADDRESS.matcher(email).matches()
                                && password.length() >= 6
                );
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
    }
}