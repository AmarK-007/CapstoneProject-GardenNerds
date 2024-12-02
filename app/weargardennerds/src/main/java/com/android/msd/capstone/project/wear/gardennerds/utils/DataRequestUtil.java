package com.android.msd.capstone.project.wear.gardennerds.utils;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;

public class DataRequestUtil {
    private static final String TAG = "DataRequestUtil";
    private static final String DATA_PATH = "/user_data";

    public static void requestUserDataFromPhone(Context context) {
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/user_data");
        DataMap dataMap = putDataMapRequest.getDataMap();
        dataMap.putString("user_data", "example_user_data"); // Replace with actual user data

        Task<DataItem> putDataTask = Wearable.getDataClient(context).putDataItem(putDataMapRequest.asPutDataRequest());
        putDataTask.addOnSuccessListener(dataItem -> Log.d("DataRequestUtil", "Data sent successfully: " + dataItem))
                .addOnFailureListener(e -> Log.e("DataRequestUtil", "Failed to send data", e));
    }
}