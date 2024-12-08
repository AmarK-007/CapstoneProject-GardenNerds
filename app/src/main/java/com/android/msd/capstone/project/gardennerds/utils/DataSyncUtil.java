package com.android.msd.capstone.project.gardennerds.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.Set;

public class DataSyncUtil {
    public static final String DATA_CAPABILITY_NAME = "data_transfer";
    public static final String UPDATE_DATA_PATH = "/update_db_data";
    private static final String TAG = DataSyncUtil.class.getSimpleName();


    // get user data Capabilities Keys
    public static final String LOGIN_USER = "login_user";
    public static final String DB_DATA_USER = "db_data_user";
    public static final String DB_DATA_GARDEN = "db_data_garden";
    public static final String DB_DATA_PLANT = "db_data_plant";
    public static final String DB_DATA_REMINDER = "db_data_reminder";


   public static void sendUserDataToWear(Context context, String action, String typeOfData, String userData) {
        Log.d(TAG, "Preparing to send data to wear: action=" + action + ", typeOfData=" + typeOfData + ", userData=" + userData);
       /* DataClient dataClient = Wearable.getDataClient(context);
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("");
        DataMap dataMap = putDataMapRequest.getDataMap();
        dataMap.putString("action", action);
        dataMap.putString("type_of_data", typeOfData);
        dataMap.putString("user_data", userData);
        dataClient.putDataItem(putDataMapRequest.asPutDataRequest())
                .addOnSuccessListener(dataItem -> Log.d(TAG, "Successfully sent user data to wear" + dataItem))
                .addOnFailureListener(e -> Log.e(TAG, "Failed to send user data to wear", e));*/

           }
/*
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
    }*/


    // MessageClient Implementation
    public static void findCapabilityClient(Activity activity) {
        Wearable.getCapabilityClient(activity)
                .getCapability(DATA_CAPABILITY_NAME, CapabilityClient.FILTER_REACHABLE)
                .addOnSuccessListener(capabilityInfo -> {
                    if (capabilityInfo != null) {
                        Log.d("CapabilityClient", "Capability found: " + capabilityInfo.getName());
                        findNodeForGraphGeneration(activity, capabilityInfo);
                    }
                }).addOnFailureListener(e -> {
                    Utility.showToast(activity, "Failed to get capabilities");
                });
    }

    private static void findNodeForGraphGeneration(Activity activity, CapabilityInfo capabilityInfo) {
        String graphNode = null;
        Set<Node> connectedNodes = capabilityInfo.getNodes();
        Log.d("NodeDiscovery", "Connected nodes: " + connectedNodes.size());
        for (Node node : connectedNodes) {
            Log.d("NodeDiscovery", "Node ID: " + node.getId() + ", Nearby: " + node.isNearby());
        }
        graphNode = pickBestNodeId(connectedNodes);
        if (graphNode == null) {
            Utility.showToast(activity, "No node found for graph generation");
        } else {
            try {
                Utility.showToast(activity, "Sending data to the nodeID: " + graphNode);
                byte[] dataBytes = LOGIN_USER.toString().getBytes();
                sendDataToPhone(activity, graphNode, dataBytes);
            } catch (Exception e) {
                e.printStackTrace();
                Utility.showToast(activity, "Failed to send data to the node");
            }
        }
    }

    private static void sendDataToPhone(Activity activity, String graphNodeId, byte[] dataToSend) {
        if (graphNodeId != null) {

            Task<Integer> sendTask = Wearable.getMessageClient(activity).sendMessage(graphNodeId, UPDATE_DATA_PATH, dataToSend);
            sendTask.addOnSuccessListener(statusInfo -> {
                Utility.showToast(activity, "Data sent successfully");
            }).addOnFailureListener(e -> {
                Utility.showToast(activity, "Failed to send data");
            });
        } else {
            Utility.showToast(activity, "No node found for graph generation");
        }
    }


    private static String pickBestNodeId(Set<Node> connectedNodes) {
        String bestNodeId = null;
        for (Node node : connectedNodes) {
            if (node.isNearby()) {
                return node.getId();
            }
            bestNodeId = node.getId();
        }
        return bestNodeId;
    }

}