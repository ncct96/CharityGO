package org.charitygo;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseCMSvc extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {

        }
        String message = remoteMessage.getNotification().getBody();
        Log.e("TAG", "Title" + remoteMessage.getNotification().getBody());
        MyNotificationManager.getInstance(this).displayNotification("hi", "daad");
    }
}
