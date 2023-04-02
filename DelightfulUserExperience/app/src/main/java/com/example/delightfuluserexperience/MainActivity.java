package com.example.delightfuluserexperience;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.LocaleConfig;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.delightfuluserexperience.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setEnabled(false);
        textWatcher(binding.editText, binding.button);

        binding.button.setOnClickListener(view -> {
            Snackbar snackbar = Snackbar.make(
                    view, "Go to second screen?", Snackbar.LENGTH_SHORT
            );
            snackbar.setAction("Go", v -> {
                // Do something when the action button is clicked
                startActivity(new Intent(getApplicationContext(), MainActivity2.class));
            });
            snackbar.setActionTextColor(getColor(R.color.purple_200));
            snackbar.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_night_mode, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_light:
                    AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                );
                return true;
            case R.id.item_dark:
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                );
                return true;
            case R.id.item_default:
                AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                );
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    public static void textWatcher(EditText editText, Button button) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Enable the button if the EditText is not empty, disable it otherwise
                button.setEnabled(charSequence.toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}