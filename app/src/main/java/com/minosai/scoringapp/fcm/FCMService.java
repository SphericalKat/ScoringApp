package com.minosai.scoringapp.fcm;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            String body = remoteMessage.getNotification().getBody();
            String title = remoteMessage.getNotification().getTitle();
            FcmNotification.notify(this, body, title);
        }
    }

    @Override
    public void onNewToken(String s) {
        Log.i("SCORING APP FCM TOKEN", s);
    }
}