package com.android.msd.capstone.project.gardennerds.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

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
        getWearDeviceId(context);
    }

    public static void sendMessageToWear(Context context, String wearDeviceID, String path, String message) {
        // The path is used to route the message on the Wear OS side
        MessageClient messageClient = Wearable.getMessageClient(context);

        messageClient.sendMessage(
                wearDeviceID,  // Replace with the Wear device ID or the recipient address
                path,                // The path where the message should be routed
                message.getBytes()   // The actual message payload as a byte array
        ).addOnCompleteListener(new OnCompleteListener<Integer>() {
            @Override
            public void onComplete(Task<Integer> task) {
                if (task.isSuccessful()) {
                    Log.d("Phone", "Message sent successfully!");
                } else {
                    Log.e("Phone", "Failed to send message", task.getException());
                }
            }
        });
    }

    public static void getWearDeviceId(Context context) {
        // Get the NodeClient
        Wearable.getNodeClient(context).getConnectedNodes()
                .addOnSuccessListener(new OnSuccessListener<List<Node>>() {
                    @Override
                    public void onSuccess(List<Node> nodes) {
                        for (Node node : nodes) {
                            // Get the node ID for each connected device
                            String wearDeviceId = node.getId();
                            Log.d("Wear", "Connected Wear Device ID: " + wearDeviceId);

                            // You can now use this ID to send messages
                            sendMessageToWear(context, wearDeviceId, DATA_PATH, "Hello from Phone!");
                        }
                    }
                });
    }
}