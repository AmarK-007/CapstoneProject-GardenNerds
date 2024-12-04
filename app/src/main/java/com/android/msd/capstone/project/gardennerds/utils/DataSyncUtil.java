package com.android.msd.capstone.project.gardennerds.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;

public class DataSyncUtil {
    private static final String TAG = "DataSyncUtil";
    private static final String DATA_PATH = "/user_data";

    public static void sendUserDataToWear(Context context, String action, String typeOfData, String userData) {
        Log.d(TAG, "Preparing to send data to wear: action=" + action + ", typeOfData=" + typeOfData + ", userData=" + userData);
        DataClient dataClient = Wearable.getDataClient(context);
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(DATA_PATH);
        DataMap dataMap = putDataMapRequest.getDataMap();
        dataMap.putString("action", action);
        dataMap.putString("type_of_data", typeOfData);
        dataMap.putString("user_data", userData);
        dataClient.putDataItem(putDataMapRequest.asPutDataRequest())
                .addOnSuccessListener(dataItem -> Log.d(TAG, "Successfully sent user data to wear" + dataItem))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to send user data to wear", e));

        Intent intent = new Intent("com.example.ACTION_SEND_DATA");
        intent.putExtra("action", action);
        intent.putExtra("type_of_data", typeOfData);
        intent.putExtra("user_data", userData);
        context.sendBroadcast(intent);
    }
}