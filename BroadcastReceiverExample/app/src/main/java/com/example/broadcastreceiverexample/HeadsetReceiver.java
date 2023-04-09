package com.example.broadcastreceiverexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class HeadsetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int state = intent.getIntExtra("state", -1);
        if (state == 1) {
            Toast.makeText(context, "Headset is plugged!", Toast.LENGTH_SHORT).show();
        } else if (state == 0){
            Toast.makeText(context, "Headset is unplugged!", Toast.LENGTH_SHORT).show();
        }
    }
}
