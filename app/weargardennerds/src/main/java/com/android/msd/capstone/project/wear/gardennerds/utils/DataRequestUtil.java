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

    public static void requestUserDataFromPhone(Context context, String action, String typeOfData, String userData) {
        DataClient dataClient = Wearable.getDataClient(context);
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(DATA_PATH);
        DataMap dataMap = putDataMapRequest.getDataMap();
        dataMap.putString("action", action);
        dataMap.putString("type_of_data", typeOfData);
        dataMap.putString("user_data", userData);

        Task<DataItem> putDataTask = dataClient.putDataItem(putDataMapRequest.asPutDataRequest());
        putDataTask.addOnSuccessListener(dataItem -> Log.d(TAG, "Data sent successfully: " + dataItem))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to send data", e));
    }
}