package com.android.msd.capstone.project.gardennerds.utils;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;

public class DataSyncUtil {
    private static final String TAG = "DataSyncUtil";
    private static final String DATA_PATH = "/user_data";

    public static void sendUserDataToWear(Context context, String userData) {
        DataClient dataClient = Wearable.getDataClient(context);
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(DATA_PATH);
        DataMap dataMap = putDataMapRequest.getDataMap();
        dataMap.putString("user_data", userData);
        dataClient.putDataItem(putDataMapRequest.asPutDataRequest())
                .addOnSuccessListener(dataItem -> Log.d(TAG, "Successfully sent user data to wear"))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to send user data to wear", e));
    }
}