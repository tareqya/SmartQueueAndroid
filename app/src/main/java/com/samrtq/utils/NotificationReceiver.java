package com.samrtq.utils;

import static com.samrtq.main.HomeActivity.createNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.samrtq.main.HomeActivity;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Call the method to create and show the notification
//        ((HomeActivity) context).createNotification();
        String body = intent.getStringExtra("body");
        createNotification(context, body);
    }

}
