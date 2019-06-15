package com.minosai.scoringapp.fcm;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.minosai.scoringapp.R;
import com.minosai.scoringapp.ui.auth.RegisterActivity;

public class FcmNotification {

    private static final String NOTIFICATION_TAG = "Fcm";

    private static String FCM_CHANNEL_ID = "com.minosai.scoringapp.FCM_CHANNEL_ID";

    public static void notify(final Context context, String message, String title) {

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context)

                .setDefaults(Notification.DEFAULT_ALL)

                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message)

                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                .setChannelId(FCM_CHANNEL_ID)

                .setContentIntent(
                        PendingIntent.getActivity(
                                context,
                                0,
                                new Intent(context, RegisterActivity.class),
                                PendingIntent.FLAG_UPDATE_CURRENT))

                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message)
                        .setBigContentTitle(title)
                        .setSummaryText("Message from VIT"))

                .setAutoCancel(true);

        notify(context, builder.build());
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(final Context context, final Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Messages";
            String description = "Messages and announces from VIT";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(FCM_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            nm.createNotificationChannel(channel);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG, 0, notification);
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(), notification);
        }
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static void cancel(final Context context) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.cancel(NOTIFICATION_TAG, 0);
        } else {
            nm.cancel(NOTIFICATION_TAG.hashCode());
        }
    }
}