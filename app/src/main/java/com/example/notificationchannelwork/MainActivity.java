package com.example.notificationchannelwork;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;
import androidx.core.app.RemoteInput;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button buttonSendNotification;

    public static ArrayList<MessageModel> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messages.add(new MessageModel("Sarah", "Hello everyone"));
        messages.add(new MessageModel("Brad", "Hi Sarah!"));
        messages.add(new MessageModel("Tom", "How is everybody!"));

        buttonSendNotification = (Button) findViewById(R.id.buttonNotification);

        buttonSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendNotification();
               // new DownloadAsyncTask().execute();
                //sendLLargeTextNotification();
                //sendBigPictureNotification();
                //sendInboxNotification();
                sendMediaNotiifcation();
                //sendMessagingStyleNotification(MainActivity.this);
                //sendAGroupOfNotification();
                //checkNotificationSettings();
                //if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) checkNotificationChannelSettings();
                //sendCustomNotification();
            }
        });
    }
    @RequiresApi(26)
    private void checkNotificationChannelSettings(){
        NotificationManager manager = getSystemService(NotificationManager.class);
        NotificationChannel channel = manager.getNotificationChannel("channel1");
        if(channel != null){
            if(channel.getImportance() != NotificationManager.IMPORTANCE_NONE);
            else{
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Notification channel is not enabled")
                        .setMessage("We need to send notification on this channel")
                        .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                                intent.putExtra(Settings.EXTRA_CHANNEL_ID, "channel1");
                                startActivity(intent);
                            }
                        });
                builder.show();
            }
        }
    }

    private void sendCustomNotification(){
        RemoteViews collapsedView = new RemoteViews(getPackageName(), R.layout.notification_collapse);
        RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.notification_expanded);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent, 0);

        expandedView.setOnClickPendingIntent(R.id.billie, pendingIntent);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.bad_guy);
        expandedView.setImageViewBitmap(R.id.image, bitmap);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel1")
                .setContentTitle("Notification")
                .setContentText("This is going to be ignored")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_bell)
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle());
        NotificationManagerCompat  manager = NotificationManagerCompat.from(this);
        manager.notify(1, builder.build());
    }

    private void checkNotificationSettings(){
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        if(!manager.areNotificationsEnabled()){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this)
                    .setTitle("Notificatioin are disabled")
                    .setMessage("Please enable the notification")
                    .setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                Log.d(TAG, "onClick: " + Build.VERSION.SDK_INT);
                                Log.d(TAG, "onClick: " + Build.VERSION_CODES.O);
                                Intent intent =  new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }

                        }
                    });
            alertDialogBuilder.show();
        }else{
            Toast.makeText(this, "Notifications are enabled", Toast.LENGTH_SHORT).show();
        }

    }

    private void sendAGroupOfNotification(){
        Log.d(TAG, "sendAGroupOfNotification: started");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel1")
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle("First Notification")
                .setContentText("This is the first notification")
                .setPriority(NotificationCompat.PRIORITY_HIGH);
                //.setGroup("first_group");

        NotificationCompat.Builder secondBuilder = new NotificationCompat.Builder(this, "channel1")
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle("Second Notification")
                .setContentText("This is the second notification")
                .setPriority(NotificationCompat.PRIORITY_HIGH);
                //.setGroup("first_group");

        NotificationCompat.Builder summaryBuilder = new NotificationCompat.Builder(this, "channel1")
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle("Summary Notification")
                .setContentText("This is the summary notification")
                .setPriority(NotificationCompat.PRIORITY_HIGH);
                //.setGroup("first_group")
                //.setGroupSummary(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        SystemClock.sleep(2000);
        manager.notify(1, builder.build());

        SystemClock.sleep(2000);
        manager.notify(2, secondBuilder.build());
        SystemClock.sleep(2000);
        manager.notify(3, secondBuilder.build());
        SystemClock.sleep(2000);
        manager.notify(4, secondBuilder.build());
        SystemClock.sleep(2000);
        manager.notify(5, secondBuilder.build());

        //SystemClock.sleep(2000);
        //manager.notify(3, summaryBuilder.build());

    }

    private void sendInboxNotification() {
        Log.d(TAG, "sendInboxNotification: started");



        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel1")
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle("Inbox notification")
                .setContentText("Somebody liked your post")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.InboxStyle()
                .addLine("First Line")
                .addLine("Second Line")
                .addLine("Third Line")
                .addLine("Fourth Line")
                .addLine("Fifth Line")
                .addLine("Sixth Line")
                .addLine("Seventh Line")
                .addLine("Eighth Line"));
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(1, builder.build());
    }

    private void sendBigPictureNotification(){
        Log.d(TAG, "sendBigPictureNotification: started");

        Bitmap moon = BitmapFactory.decodeResource(getResources(), R.mipmap.moon);



        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel1")
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle("Big picture notification")
                .setContentText("Somebody liked your post")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setLargeIcon(moon)
                .setStyle(new NotificationCompat.BigPictureStyle()
                .setBigContentTitle("Big picture notification")
                .bigPicture(moon)
                .bigLargeIcon(null));
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(1, builder.build());
    }

    public static void sendMessagingStyleNotification(Context context){
        NotificationCompat.MessagingStyle style = new NotificationCompat.MessagingStyle(new Person.Builder()
                .setName("Meisam").build());
        style.setConversationTitle("Family Group");
        for(MessageModel m: messages){
            style.addMessage(new NotificationCompat.MessagingStyle.Message(m.getText(), SystemClock.currentThreadTimeMillis(),
                    new Person.Builder().setName(m.getSender()).build()));
        }

        RemoteInput remoteInput = new RemoteInput.Builder("our_remote_input")
                .setLabel("Your answer here")
                .build();

        Intent intent = new Intent(context, MessageBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);


        NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.ic_bell, "Reply", pendingIntent)
                .addRemoteInput(remoteInput).build();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel1")
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle("Messaging notification")
                .setContentText("Messages")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(style)
                .addAction(action)
                .setColor(Color.GREEN);
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(1, builder.build());
    }
    
    private void sendMediaNotiifcation(){
        Log.d(TAG, "sendMediaNotiifcation: started");

        Bitmap cover = BitmapFactory.decodeResource(getResources(), R.mipmap.bad_guy);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel1")
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle("Bad guy")
                .setContentText("By Billie Eilish")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.ic_dislike, "Dislike", null)
                .addAction(R.drawable.ic_previous, "Previous", null)
                .addAction(R.drawable.ic_pause, "Pause", null)
                .addAction(R.drawable.ic_next, "Next", null)
                .addAction(R.drawable.ic_like, "Like", null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(1, 2, 3))
                .setLargeIcon(cover);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(1, builder.build());
    }

    private void sendNotification(){
        Log.d(TAG, "sendNotification: started");

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, intent, 0);

        Intent actionIntent = new Intent(this, ToastReceiver.class);
        actionIntent.putExtra(getResources().getResourceName(R.string.message), "It's a toast message......");
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(this, 3, actionIntent, 0);
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(
                R.drawable.ic_add, "Toast", actionPendingIntent
        ).build();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel1")
                .setContentTitle("Hello")
                .setSmallIcon(R.drawable.ic_bell)
                .setContentText("This is my first notification. Bangladesh is our homeland. This is not a very big country, but this is a very beautiful country.....")
                .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
                .setColor(Color.RED)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .addAction(action);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(1, builder.build());

    }

    private void sendLLargeTextNotification(){
        Log.d(TAG, "sendLLargeTextNotification: started");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel1")
                .setSmallIcon(R.drawable.ic_bell)
                .setContentTitle("Larger text Notification")
                .setContentText("This is the content of the notification")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle()
                .setBigContentTitle("Large Text Title")
                .bigText(getResources().getString(R.string.large_text))
                .setSummaryText("This is the content of the notification"));
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(1, builder.build());
    }

    private class DownloadAsyncTask extends AsyncTask<Void, Integer, Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            for(int i = 0; i < 100; i += 10){
                try { Thread.sleep(1000); }
                catch (InterruptedException e) { e.printStackTrace(); }
                publishProgress(i);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "channel1")
                    .setContentTitle("Downloading")
                    .setProgress(100, values[0], true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSmallIcon(R.drawable.ic_add)
                    .setOnlyAlertOnce(true);
            NotificationManagerCompat manager = NotificationManagerCompat.from(MainActivity.this);
            manager.notify(5, builder.build());
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "channel1")
                    .setContentTitle("Downloaded")
                    .setContentText("Download Finished")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setSmallIcon(R.drawable.ic_add)
                    .setOnlyAlertOnce(true);
            NotificationManagerCompat manager = NotificationManagerCompat.from(MainActivity.this);
            manager.notify(5, builder.build());
        }
    }
}