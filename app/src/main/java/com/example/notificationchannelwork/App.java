package com.example.notificationchannelwork;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationManagerCompat;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    private void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationManager manager = getSystemService(NotificationManager.class);
            if(manager != null) {
                manager.createNotificationChannelGroup(new NotificationChannelGroup("group1", "First group"));
                manager.createNotificationChannelGroup(new NotificationChannelGroup("group2", "Second group"));
            }

            NotificationChannel channel1 = new NotificationChannel("channel1", "Channel1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This channel is important for social activities on your profile");
            channel1.setGroup("group1");

            NotificationChannel channel2 = new NotificationChannel("channel2", "Channel2", NotificationManager.IMPORTANCE_HIGH);
            channel2.setDescription("This channel is important for social activities on your profile");
            channel2.setGroup("group2");

            NotificationChannel channel3 = new NotificationChannel("channel3", "Channel3", NotificationManager.IMPORTANCE_HIGH);
            channel3.setDescription("This channel is important for social activities on your profile");
            //channel3.setGroup("group1");

            if(manager != null){
                manager.createNotificationChannel(channel1);
                manager.createNotificationChannel(channel2);
                manager.createNotificationChannel(channel3);
            }

        }
    }
}
