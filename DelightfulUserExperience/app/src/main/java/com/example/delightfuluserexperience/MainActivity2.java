package com.example.delightfuluserexperience;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;

import com.example.delightfuluserexperience.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;

    private int progressValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressValue = binding.progressBar.getProgress();

        binding.imageViewAdd.setOnClickListener(view -> {
            if (progressValue < 100) {
                binding.progressBar.setProgress(progressValue++);
                binding.textView.setText(progressValue+"");
            }

        });

        binding.imageViewRemove.setOnClickListener(view -> {
            if (progressValue > 0) {
                binding.progressBar.setProgress(progressValue--);
                binding.textView.setText(progressValue+"");
            }
        });
    }
}