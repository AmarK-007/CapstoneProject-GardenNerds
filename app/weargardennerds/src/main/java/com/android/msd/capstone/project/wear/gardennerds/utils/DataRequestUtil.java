package com.android.msd.capstone.project.gardennerds.utils;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

public class DataRequestUtil {
    private static final String TAG = "DataRequestUtil";
    private static final String DATA_PATH = "/user_data";

    public static void requestUserDataFromPhone(Context context) {
        DataClient dataClient = Wearable.getDataClient(context);
        dataClient.getDataItems()
                .addOnSuccessListener(dataItems -> {
                    for (DataItem dataItem : dataItems) {
                        if (dataItem.getUri().getPath().equals(DATA_PATH)) {
                            DataMap dataMap = DataMapItem.fromDataItem(dataItem).getDataMap();
                            String userData = dataMap.getString("user_data");
                            Log.d(TAG, "Received user data from phone: " + userData);
                            // Handle the received user data
                        }
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "Failed to request user data from phone", e));
    }
}