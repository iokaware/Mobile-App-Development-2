package com.example.notificationsexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import com.example.notificationsexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private NotificationManager notificationManager;

    public static final String CHANNEL_ID = "primary_channel";
    public static final int NOTIFICATION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createChannel();

        Button button = findViewById(R.id.buttonNotify);

        Notification myNot = getNotification();

        button.setOnClickListener(view -> {
            notificationManager.notify(NOTIFICATION_ID,
                    myNot);
        });
    }

    public void createChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.
                os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new
                    NotificationChannel(
                    CHANNEL_ID,
                    "Primary Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel
                    (channel);
        }
    }

    public Notification getNotification() {
        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(
                this, CHANNEL_ID
        );
        builder.setSmallIcon(R.drawable.ic_android)
                .setContentTitle("Title")
                .setContentText("Text")
                .setContentIntent(getPending())
                .addAction(
                        R.drawable.ic_update,
                        "Update",
                        getPending()
                )
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(
                                getResources(), R.drawable.image
                        )));
        return builder.build();
    }

    public PendingIntent getPending() {
        return PendingIntent.getActivity(
                this,
                NOTIFICATION_ID,
                new Intent(this,
                        SecondActivity.class),
                PendingIntent.FLAG_IMMUTABLE
        );
    }
}