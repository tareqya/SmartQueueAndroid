package com.samrtq.utils;

import static com.samrtq.main.HomeActivity.createNotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String body = intent.getStringExtra("body");
        createNotification(context, body);
    }

}
