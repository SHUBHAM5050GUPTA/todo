package com.example.shubhamgupta.todo1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmReciever extends BroadcastReceiver {
    public AlarmReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        int Todo_Id=Integer.parseInt(intent.getStringExtra("TODO_ID"));
        String Todo_Title=intent.getStringExtra("TODO_TITLE");

        NotificationCompat.Builder mBuiilder=new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(Todo_Title)
                .setAutoCancel(false)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentText("ALARM");


        Intent resultIntent=new Intent(context,ToDoDetailActivity.class);
        PendingIntent resultPendingIntent=PendingIntent.getActivity(context,Todo_Id,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuiilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(Todo_Id,mBuiilder.build());
    }
}
