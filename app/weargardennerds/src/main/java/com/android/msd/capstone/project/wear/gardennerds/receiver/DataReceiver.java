package com.android.msd.capstone.project.wear.gardennerds.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DataReceiver extends BroadcastReceiver {
    // Tag for logging messages
    private static final String TAG = "DataReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Check if the received intent matches the expected action
        if ("com.example.ACTION_SEND_DATA".equals(intent.getAction())) {
            String action = intent.getStringExtra("action");
            String typeOfData = intent.getStringExtra("type_of_data");
            String userData = intent.getStringExtra("user_data");
            // Handle received data
            Log.d(TAG, "Received action: " + action + ", typeOfData: " + typeOfData + ", userData: " + userData);
        }
    }
}
