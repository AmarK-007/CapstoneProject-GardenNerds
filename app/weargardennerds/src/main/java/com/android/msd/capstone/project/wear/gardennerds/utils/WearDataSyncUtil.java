package com.android.msd.capstone.project.wear.gardennerds.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.msd.capstone.project.wear.gardennerds.db.GardenDataSource;
import com.android.msd.capstone.project.wear.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.wear.gardennerds.db.ReminderDataSource;
import com.android.msd.capstone.project.wear.gardennerds.db.UserDataSource;
import com.android.msd.capstone.project.wear.gardennerds.models.Garden;
import com.android.msd.capstone.project.wear.gardennerds.models.Plant;
import com.android.msd.capstone.project.wear.gardennerds.models.Reminder;
import com.android.msd.capstone.project.wear.gardennerds.models.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.Set;

public class WearDataSyncUtil {
    // Constants for capability and data paths
    public static final String DATA_CAPABILITY_NAME = "data_transfer";
    public static final String UPDATE_DATA_PATH = "/update_db_data";
    private static final String TAG = WearDataSyncUtil.class.getSimpleName();

    // Capability keys for user data
    public static final String LOGIN_USER = "login_user";
    public static final String DB_DATA_USER = "db_data_user";
    public static final String DB_DATA_GARDEN = "db_data_garden";
    public static final String DB_DATA_PLANT = "db_data_plant";
    public static final String DB_DATA_REMINDER = "db_data_reminder";

    /**
     * Finds the capability client for data transfer.
     *
     * @param activity The activity context.
     */
    public static void findCapabilityClient(Activity activity) {
        Wearable.getCapabilityClient(activity)
                .getCapability(DATA_CAPABILITY_NAME, CapabilityClient.FILTER_REACHABLE)
                .addOnSuccessListener(capabilityInfo -> {
                    if (capabilityInfo != null) {
                        Log.d("CapabilityClient", "Capability found: " + capabilityInfo.getName());
                        findNodeForDataTransfer(activity, capabilityInfo);
                    }
                }).addOnFailureListener(e -> {
                    Log.v(TAG, "Failed to get capabilities");
                });
    }

    /**
     * Finds the best node for data transfer.
     *
     * @param activity The activity context.
     * @param capabilityInfo The capability information.
     */
    private static void findNodeForDataTransfer(Activity activity, CapabilityInfo capabilityInfo) {
        String graphNode = null;
        Set<Node> connectedNodes = capabilityInfo.getNodes();
        Log.d("NodeDiscovery", "Connected nodes: " + connectedNodes.size());
        for (Node node : connectedNodes) {
            Log.d("NodeDiscovery", "Node ID: " + node.getId() + ", Nearby: " + node.isNearby());
        }
        graphNode = pickBestNodeId(connectedNodes);
        if (graphNode == null) {
            Log.v(TAG, "No node found for data transfer");
        } else {
            try {
                Log.v(TAG, "Sending data to the nodeID: " + graphNode);
                byte[] dataBytes = LOGIN_USER.toString().getBytes();
                sendDataToPhone(activity, graphNode, dataBytes);
            } catch (Exception e) {
                e.printStackTrace();
                Log.v(TAG, "Failed to send data to the node");
            }
        }
    }

    /**
     * Sends data to the phone.
     *
     * @param activity The activity context.
     * @param graphNodeId The node ID to send data to.
     * @param dataToSend The data to send.
     */
    private static void sendDataToPhone(Activity activity, String graphNodeId, byte[] dataToSend) {
        if (graphNodeId != null) {
            Task<Integer> sendTask = Wearable.getMessageClient(activity).sendMessage(graphNodeId, UPDATE_DATA_PATH, dataToSend);
            sendTask.addOnSuccessListener(statusInfo -> {
                Log.v(TAG, "Data sent successfully");
            }).addOnFailureListener(e -> {
                Log.v(TAG, "Failed to send data");
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
     * Handles data request received from the phone.
     *
     * @param context The context.
     * @param messageReceived The message received.
     */
    public static void handleDataRequest(Context context, String messageReceived) {
        String[] messageParts = messageReceived.split(" :~: ");
        if (messageParts != null && messageParts.length == 3) {
            String action = messageParts[0];
            String typeOfData = messageParts[1];
            String userData = messageParts[2];
            handleUserData(context, action, typeOfData, userData);
        } else {
            Log.w(TAG, "Invalid data request: " + messageReceived);
        }
    }

    /**
     * Handles user data based on the action and type of data.
     *
     * @param context The context.
     * @param action The action to perform (insert, update, delete).
     * @param typeOfData The type of data (user, garden, plant, reminder).
     * @param userData The user data.
     */
    private static void handleUserData(Context context, String action, String typeOfData, String userData) {
        Log.d(TAG, "Handling user data: action=" + action + ", typeOfData=" + typeOfData + ", userData=" + userData);

        if (UserDataSource.TABLE_NAME.equals(typeOfData) && userData != null && !userData.isEmpty()) {
            UserDataSource userDataSource = new UserDataSource(context);
            String[] dataParts = userData.split(", ");
            User user = new User();
            user.setUserId(Integer.parseInt(dataParts[0].split(": ")[1]));
            user.setName(dataParts[1].split(": ")[1]);
            user.setEmail(dataParts[2].split(": ")[1]);
            user.setUsername(dataParts[3].split(": ")[1]);
            user.setPurchaseHistory(dataParts[4].split(": ")[1]);
            user.setShippingAddress1(dataParts[5].split(": ")[1]);
            user.setShippingAddress2(dataParts[6].split(": ")[1]);
            user.setCity(dataParts[7].split(": ")[1]);
            user.setProvince(dataParts[8].split(": ")[1]);
            user.setPincode(dataParts[9].split(": ")[1]);

            switch (action) {
                case "insert":
                    userDataSource.insertUser(user);
                    break;
                case "update":
                    userDataSource.updateUser(user);
                    break;
                case "delete":
                    userDataSource.deleteUser(user);
                    break;
                default:
                    Log.w(TAG, "Unknown action: " + action);
                    break;
            }
        } else if (GardenDataSource.TABLE_NAME.equals(typeOfData) && userData != null && !userData.isEmpty()) {
            GardenDataSource gardenDataSource = new GardenDataSource(context);
            String[] dataParts = userData.split(", ");
            Garden garden = new Garden();
            garden.setGardenId(Integer.parseInt(dataParts[0].split(": ")[1]));
            garden.setName(dataParts[1].split(": ")[1]);
            garden.setDescription(dataParts[2].split(": ")[1]);
            garden.setGardenArea(dataParts[3].split(": ")[1]);
            garden.setGardenLatitude(dataParts[4].split(": ")[1]);
            garden.setGardenLongitude(dataParts[5].split(": ")[1]);
            garden.setSunlightPreference(dataParts[6].split(": ")[1]);
            garden.setWateringFrequency(dataParts[7].split(": ")[1]);
            garden.setImageUri(dataParts[8].split(": ")[1]);
            garden.setUserId(Integer.parseInt(dataParts[9].split(": ")[1]));

            switch (action) {
                case "insert":
                    gardenDataSource.insertGarden(garden);
                    break;
                case "update":
                    gardenDataSource.updateGarden(garden);
                    break;
                case "delete":
                    gardenDataSource.deleteGarden(garden);
                    break;
                default:
                    Log.w(TAG, "Unknown action: " + action);
                    break;
            }

        } else if (PlantDataSource.TABLE_NAME.equals(typeOfData) && userData != null && !userData.isEmpty()) {
            PlantDataSource plantDataSource = new PlantDataSource(context);
            String[] dataParts = userData.split(", ");
            Plant plant = new Plant();
            plant.setPlantId(Integer.parseInt(dataParts[0].split(": ")[1]));
            plant.setPlantName(dataParts[1]);
            plant.setPlantType(dataParts[2]);
            plant.setSunlightLevel(dataParts[3]);
            plant.setMoistureLevel(dataParts[4]);
            plant.setTemperatureLevel(dataParts[5]);
            plant.setWateringInterval(dataParts[6]);
            plant.setNutrientRequired(dataParts[7]);
            plant.setImageUri(dataParts[8]);
            plant.setGardenId(Integer.parseInt(dataParts[9]));

            switch (action) {
                case "insert":
                    plantDataSource.insertPlant(plant);
                    break;
                case "update":
                    plantDataSource.updatePlant(plant);
                    break;
                case "delete":
                    plantDataSource.deletePlant(plant);
                    break;
                default:
                    Log.w(TAG, "Unknown action: " + action);
                    break;
            }

        } else if (ReminderDataSource.TABLE_NAME.equals(typeOfData) && userData != null && !userData.isEmpty()) {
            ReminderDataSource reminderDataSource = new ReminderDataSource(context);
            String[] dataParts = userData.split(", ");
            Reminder reminder = new Reminder();
            reminder.setReminderId(Integer.parseInt(dataParts[0].split(": ")[1]));
            reminder.setDateTime(dataParts[1].split(": ")[1]);
            reminder.setPlantId(Integer.parseInt(dataParts[2].split(": ")[1]));
            reminder.setReminderTypeId(Integer.parseInt(dataParts[3].split(": ")[1]));
            reminder.setFrequency(dataParts[4].split(": ")[1]);
            reminder.setMoistureLevel(dataParts[5].split(": ")[1]);
            reminder.setTemperatureLevel(dataParts[6].split(": ")[1]);
            reminder.setSunlightLevel(dataParts[7].split(": ")[1]);
            reminder.setNutrientRequired(dataParts[8].split(": ")[1]);

            switch (action) {
                case "insert":
                    reminderDataSource.insertReminder(reminder);
                    break;
                case "update":
                    reminderDataSource.updateReminder(reminder);
                    break;
                case "delete":
                    reminderDataSource.deleteReminder(reminder);
                    break;
                default:
                    Log.w(TAG, "Unknown action: " + action);
                    break;
            }

        } else if (typeOfData.equals("userName") && userData != null && !userData.isEmpty()) {
            UserDataSource userDataSource = new UserDataSource(context);
            boolean isUserExists = userDataSource.checkUser(userData);
            Log.d(TAG, "User exists: " + isUserExists);
            if (isUserExists) {
                saveUserDataInPreference(context, userData);
            }
        } else {
            Log.w(TAG, "Unknown type of data: " + typeOfData);
        }
    }

    /**
     * Saves user data in shared preferences.
     *
     * @param context The context.
     * @param username The username to save.
     */
    private static void saveUserDataInPreference(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Login_Username", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", username);
        editor.apply();
    }

    /**
     * Reads user data from shared preferences.
     *
     * @param context The context.
     * @return The username.
     */
    public static String readUserDataFromPreference(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Login_Username", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userName", null);
    }
}