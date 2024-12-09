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
    // Constants for capability and data paths
    public static final String DATA_CAPABILITY_NAME = "data_transfer";
    public static final String UPDATE_DATA_PATH = "/update_db_data";
    private static final String TAG = PhoneDataSyncUtil.class.getSimpleName();

    // Capability keys for user data
    public static final String LOGIN_USER = "login_user";
    public static final String DB_DATA_USER = "db_data_user";
    public static final String DB_DATA_GARDEN = "db_data_garden";
    public static final String DB_DATA_PLANT = "db_data_plant";
    public static final String DB_DATA_REMINDER = "db_data_reminder";

    /**
     * Sends user data to the Wear OS device.
     *
     * @param context The context.
     * @param action The action to perform (insert, update, delete).
     * @param typeOfData The type of data (user, garden, plant, reminder).
     * @param userData The user data.
     */
    public static void sendUserDataToWear(Context context, String action, String typeOfData, String userData) {
        Log.d(TAG, "Preparing to send data to wear: action=" + action + ", typeOfData=" + typeOfData + ", userData=" + userData);
        findCapabilityClient(context, action, typeOfData, userData);
    }

    /**
     * Finds the capability client for data transfer.
     *
     * @param context The context.
     * @param action The action to perform.
     * @param typeOfData The type of data.
     * @param userData The user data.
     */
    public static void findCapabilityClient(Context context, String action, String typeOfData, String userData) {
        Wearable.getCapabilityClient(context)
                .getCapability(DATA_CAPABILITY_NAME, CapabilityClient.FILTER_REACHABLE)
                .addOnSuccessListener(capabilityInfo -> {
                    if (capabilityInfo != null) {
                        Log.v("CapabilityClient", "Capability found: " + capabilityInfo.getName());
                        findNodeForDataTransfer(context, capabilityInfo, action, typeOfData, userData);
                    }
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to get capabilities", e);
                });
    }

    /**
     * Finds the best node for data transfer.
     *
     * @param context The context.
     * @param capabilityInfo The capability information.
     * @param action The action to perform.
     * @param typeOfData The type of data.
     * @param userData The user data.
     */
    private static void findNodeForDataTransfer(Context context, CapabilityInfo capabilityInfo, String action, String typeOfData, String userData) {
        String graphNode = null;
        Set<Node> connectedNodes = capabilityInfo.getNodes();
        Log.d("NodeDiscovery", "Connected nodes: " + connectedNodes.size());
        for (Node node : connectedNodes) {
            Log.v("NodeDiscovery", "Node ID: " + node.getId() + ", Nearby: " + node.isNearby());
        }
        graphNode = pickBestNodeId(connectedNodes);
        if (graphNode == null) {
            Log.v(TAG, "No node found for data transfer");
        } else {
            try {
                Log.v(TAG, "Sending data to the nodeID: " + graphNode);
                byte[] dataBytes = loadUserData(action, typeOfData, userData).getBytes();
                sendDataToPhone(context, graphNode, dataBytes);
            } catch (Exception e) {
                Log.e(TAG, "Failed to send data to the node", e);
            }
        }
    }

    /**
     * Sends data to the phone.
     *
     * @param context The context.
     * @param graphNodeId The node ID to send data to.
     * @param dataToSend The data to send.
     */
    private static void sendDataToPhone(Context context, String graphNodeId, byte[] dataToSend) {
        if (graphNodeId != null) {
            Task<Integer> sendTask = Wearable.getMessageClient(context).sendMessage(graphNodeId, UPDATE_DATA_PATH, dataToSend);
            sendTask.addOnSuccessListener(statusInfo -> {
                Log.v(TAG, "Data sent successfully");
            }).addOnFailureListener(e -> {
                Log.e(TAG, "Failed to send data", e);
            });
        } else {
            Log.v(TAG, "No node found for data transfer");
        }
    }

    /**
     * Picks the best node ID from the connected nodes.
     *
     * @param connectedNodes The set of connected nodes.
     * @return The best node ID.
     */
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

    /**
     * Loads user data from the database based on the action.
     *
     * @param action The action to perform.
     * @param typeOfData The type of data.
     * @param userData The user data.
     * @return The formatted user data.
     */
    private static String loadUserData(String action, String typeOfData, String userData) {
        if (action.equals("login") || action.equals("insert") || action.equals("update") || action.equals("delete")) {
            return action + " :~:" + typeOfData + " :~: " + userData;
        } else {
            Log.w(TAG, "Unknown action: " + action);
        }
        return null;
    }
}