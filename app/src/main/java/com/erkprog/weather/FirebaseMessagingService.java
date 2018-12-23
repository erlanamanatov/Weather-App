package com.erkprog.weather;

import android.util.Log;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
  private static final String TAG = "MyFirebaseService";

  @Override
  public void onNewToken(String token) {
    Log.d(TAG, "Refreshed token: " + token);
  }
}
