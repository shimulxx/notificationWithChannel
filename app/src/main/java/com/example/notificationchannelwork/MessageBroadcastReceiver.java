package com.example.notificationchannelwork;

import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class MessageBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = RemoteInput.getResultsFromIntent(intent);
        if(bundle != null){
            String message = bundle.getString("our_remote_input");
            MessageModel messageModel = new MessageModel("Meisam", message);
            MainActivity.messages.add(messageModel);
            MainActivity.sendMessagingStyleNotification(context);
        }
    }
}
