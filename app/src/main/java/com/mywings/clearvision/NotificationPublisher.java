package com.mywings.clearvision;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    public static String CHANNEL_ID = "ClearVision";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);

        NotificationChannel channel = new NotificationChannel(
                NotificationPublisher.CHANNEL_ID,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
        notificationManager.notify(id, notification);
    }
}
