package com.example.broadcastreceiverexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    HeadsetReceiver headsetReceiver;
    PowerConnectionReceiver powerConnectionReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        headsetReceiver = new HeadsetReceiver();
        powerConnectionReceiver = new PowerConnectionReceiver();

        IntentFilter headsetFilter = new IntentFilter(
                Intent.ACTION_HEADSET_PLUG);

        IntentFilter powerFilter = new IntentFilter();
        powerFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        powerFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

//        registerReceiver(headsetReceiver, headsetFilter);
        registerReceiver(powerConnectionReceiver, powerFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        unregisterReceiver(headsetReceiver);
        unregisterReceiver(powerConnectionReceiver);
    }
}