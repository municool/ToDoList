package com.example.bherrl.todolist;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by bdomij on 23.03.2016.
 */
public class Notifications extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notifIntent = new Intent(context, MainActivity.class);

        //STACK
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notifIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder
                        .setContentTitle("Oh my Gosh ...")
                        .setSmallIcon(R.mipmap.ic_tick)
                        .setContentText("What is this.. A notification")
                        .setContentIntent(pendingIntent).build();

        //Bit to be bitwise-ored into the flags field that should be set if the notification should be canceled when it is clicked by the user
        //The Line below makes the Notification disappear when clicked
        notification.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
        notification.flags = Notification.FLAG_SHOW_LIGHTS;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

    }
}
