package com.android.msd.capstone.project.gardennerds.utils;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.Set;

public class PhoneDataSyncUtil {
    public static final String DATA_CAPABILITY_NAME = "data_transfer";
    public static final String UPDATE_DATA_PATH = "/update_db_data";
    private static final String TAG = PhoneDataSyncUtil.class.getSimpleName();


    // get user data Capabilities Keys
    public static final String LOGIN_USER = "login_user";
    public static final String DB_DATA_USER = "db_data_user";
    public static final String DB_DATA_GARDEN = "db_data_garden";
    public static final String DB_DATA_PLANT = "db_data_plant";
    public static final String DB_DATA_REMINDER = "db_data_reminder";


    public static void sendUserDataToWear(Context context, String action, String typeOfData, String userData) {
        Log.d(TAG, "Preparing to send data to wear: action=" + action + ", typeOfData=" + typeOfData + ", userData=" + userData);
        findCapabilityClient(context, action, typeOfData, userData);

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
    public static void findCapabilityClient(Context context, String action, String typeOfData, String userData) {
        Wearable.getCapabilityClient(context)
                .getCapability(DATA_CAPABILITY_NAME, CapabilityClient.FILTER_REACHABLE)
                .addOnSuccessListener(capabilityInfo -> {
                    if (capabilityInfo != null) {
                        Log.v("CapabilityClient", "Capability found: " + capabilityInfo.getName());
                        findNodeForDataTransfer(context, capabilityInfo, action, typeOfData, userData);
                    }
                }).addOnFailureListener(e -> {
                    //Utility.showToast(context, "Failed to get capabilities");
                    Log.e(TAG, "Failed to get capabilities", e);
                });
    }

    private static void findNodeForDataTransfer(Context context, CapabilityInfo capabilityInfo, String action, String typeOfData, String userData) {
        String graphNode = null;
        Set<Node> connectedNodes = capabilityInfo.getNodes();
        Log.d("NodeDiscovery", "Connected nodes: " + connectedNodes.size());
        for (Node node : connectedNodes) {
            Log.v("NodeDiscovery", "Node ID: " + node.getId() + ", Nearby: " + node.isNearby());
        }
        graphNode = pickBestNodeId(connectedNodes);
        if (graphNode == null) {
            //Utility.showToast(context, "No node found for data transfer");
            Log.v(TAG, "No node found for data transfer");
        } else {
            try {
                //Utility.showToast(context, "Sending data to the nodeID: " + graphNode);
                Log.v(TAG, "Sending data to the nodeID: " + graphNode);
                byte[] dataBytes = loadUserData(action, typeOfData, userData).getBytes();
                sendDataToPhone(context, graphNode, dataBytes);
            } catch (Exception e) {
                e.printStackTrace();
                //Utility.showToast(context, "Failed to send data to the node");
                Log.e(TAG, "Failed to send data to the node", e);
            }
        }
    }

    private static void sendDataToPhone(Context context, String graphNodeId, byte[] dataToSend) {
        if (graphNodeId != null) {

            Task<Integer> sendTask = Wearable.getMessageClient(context).sendMessage(graphNodeId, UPDATE_DATA_PATH, dataToSend);
            sendTask.addOnSuccessListener(statusInfo -> {
                //Utility.showToast(context, "Data sent successfully");
                Log.v(TAG, "Data sent successfully");
            }).addOnFailureListener(e -> {
                //Utility.showToast(context, "Failed to send data");
                Log.e(TAG, "Failed to send data", e);
            });
        } else {
            //Utility.showToast(context, "No node found for data transfer");
            Log.v(TAG, "No node found for data transfer");
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

    private static String loadUserData(String action, String typeOfData, String userData) {
        // Load user data from the database based on the action
        if (action.equals("login") || action.equals("insert") || action.equals("update") || action.equals("delete")) {
            return action + " :~:" + typeOfData + " :~: " + userData;
        } else {
            Log.w(TAG, "Unknown action: " + action);
        }
        return null;
    }

}