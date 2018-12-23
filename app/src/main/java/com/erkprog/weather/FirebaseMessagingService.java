package com.erkprog.weather;

import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
  private static final String TAG = "MyFirebaseService";

  @Override
  public void onNewToken(String token) {
    Log.d(TAG, "Refreshed token: " + token);
  }

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    if (remoteMessage.getData().size() > 0) {
      Log.d(TAG, "onMessageReceived: remoteMessage Data: " + remoteMessage.getData());
    }

    if (remoteMessage.getNotification() != null) {
      Log.d(TAG, "onMessageReceived: Notification body " + remoteMessage.getNotification().getBody());
      Log.d(TAG, "onMessageReceived: Notification title " + remoteMessage.getNotification().getTitle());
    }
  }

  @Override
  public void onDeletedMessages() {
    super.onDeletedMessages();
  }

}
