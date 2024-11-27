package com.android.msd.capstone.project.gardennerds.service;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

public class PhoneDataLayerListenerService extends WearableListenerService {
    private static final String TAG = "PhoneDataLayerListener";
    private static final String DATA_PATH = "/user_data";

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                if (event.getDataItem().getUri().getPath().equals(DATA_PATH)) {
                    DataMap dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();
                    String userData = dataMap.getString("user_data");
                    Log.d(TAG, "Received user data: " + userData);
                    // Handle the received user data
                }
            }
        }
    }
}