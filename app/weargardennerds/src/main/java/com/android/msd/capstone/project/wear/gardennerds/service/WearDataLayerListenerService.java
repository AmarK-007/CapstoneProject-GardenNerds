package com.android.msd.capstone.project.wear.gardennerds.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.msd.capstone.project.wear.gardennerds.db.DBHelper;
import com.android.msd.capstone.project.wear.gardennerds.db.GardenDataSource;
import com.android.msd.capstone.project.wear.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.wear.gardennerds.db.ReminderDataSource;
import com.android.msd.capstone.project.wear.gardennerds.db.UserDataSource;
import com.android.msd.capstone.project.wear.gardennerds.models.Garden;
import com.android.msd.capstone.project.wear.gardennerds.models.Reminder;
import com.android.msd.capstone.project.wear.gardennerds.models.User;
import com.android.msd.capstone.project.wear.gardennerds.models.Plant;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

public class WearDataLayerListenerService extends WearableListenerService {
    private static final String TAG = "WearDataLayerListener";
    private static final String DATA_PATH = "/user_data";


    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d(TAG, "onDataChanged called with dataEvents: " + dataEvents);
        for (DataEvent event : dataEvents) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                if (event.getDataItem().getUri().getPath().equals(DATA_PATH)) {
                    DataMap dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();
                    String action = dataMap.getString("action");
                    String typeOfData = dataMap.getString("type_of_data");
                    String userData = dataMap.getString("user_data");
                    Log.d(TAG, "Received action: " + action + ", user data: " + userData);
                    handleUserData(action, typeOfData, userData);
                }
            }
        }
    }

    private void handleUserData(String action, String typeOfData, String userData) {
        Log.d(TAG, "Handling user data: action=" + action + ", typeOfData=" + typeOfData + ", userData=" + userData);
        DBHelper dbHelper = DBHelper.getInstance(this);

        if (UserDataSource.TABLE_NAME.equals(typeOfData) && userData != null && !userData.isEmpty()) {
            UserDataSource userDataSource = new UserDataSource(this);
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
            GardenDataSource gardenDataSource = new GardenDataSource(this);
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
            PlantDataSource plantDataSource = new PlantDataSource(this);
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
            ReminderDataSource reminderDataSource = new ReminderDataSource(this);
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
            UserDataSource userDataSource = new UserDataSource(this);
            boolean isUserExists = userDataSource.checkUser(userData);
            Log.d(TAG, "User exists: " + isUserExists);
            if (isUserExists) {
                //send user data to wear
                saveUserDataInPreference(userData);
            }
        } else {
            Log.w(TAG, "Unknown type of data: " + typeOfData);
        }
    }

    private void saveUserDataInPreference(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("Login_Username", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", username);
        editor.apply();
        //Utility.storeUser(userDataSource.getUser(username, password), this);


    }
}