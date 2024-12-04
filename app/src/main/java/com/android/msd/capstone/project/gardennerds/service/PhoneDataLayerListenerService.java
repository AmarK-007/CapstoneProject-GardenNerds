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
                    String action = dataMap.getString("action");
                    String typeOfData = dataMap.getString("type_of_data");
                    String userData = dataMap.getString("user_data");
                    Log.d(TAG, "Received action: " + action + ", user data: " + userData);
                    handleUserData(action, typeOfData, userData);
                }
            }
        }
    }

    private void handleUserData(String action, String typeOfData, String userData) {
        switch (action) {
            case "insert":
                if (typeOfData.equals("user")) {
                    // Handle user data
                } else if (typeOfData.equals("plant")) {

                }
                break;
            case "update":
                // Handle update action
                if (typeOfData.equals("user")) {
                    // Handle user data
                } else if (typeOfData.equals("plant")) {

                }
                break;
            case "delete":
                // Handle delete action
                if (typeOfData.equals("user")) {
                    // Handle user data
                } else if (typeOfData.equals("plant")) {

                }
                break;
            default:
                Log.w(TAG, "Unknown action: " + action);
                break;
        }
    }
}