package com.android.msd.capstone.project.gardennerds.broadcastReceivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationDismissReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Dismiss the notification
        int notificationId = intent.getIntExtra("notificationID",0);
        Log.d(this.toString(), "noti id = "+ notificationId);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancel(notificationId);  // Cancel the notification with the ID 0
        }
    }
}
