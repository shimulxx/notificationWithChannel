package com.example.notificationchannelwork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

public class ToastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null){
            String message = intent.getStringExtra(context.getResources().getResourceName(R.string.message));
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            NotificationManagerCompat manager = NotificationManagerCompat.from(context);
            manager.cancel(1);
        }
    }
}
