package com.android.msd.capstone.project.gardennerds.broadcastReceivers;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.activity.HomeActivity;
import com.android.msd.capstone.project.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.gardennerds.models.Plant;

public class ReminderReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "PlantWateringReminder";
    private String contentTitle;
    private String contentText;
    private int largeIcon;
    private String notificationText;

    @SuppressLint("NotificationPermission")
    @Override
    public void onReceive(Context context, Intent intent) {


//        Intent popupIntent = new Intent("SHOW_POPUP");
//        popupIntent.putExtra("showPopUP", true);
//        popupIntent.putExtra("message", "Time to water your plants!");
//        popupIntent.putExtra("ReminderType", "Fertilize");
//        context.sendBroadcast(popupIntent);
//        if (isAppInForeground(context)) {
//            // If the app is in the foreground, show the alert dialog
//            showPopup(context);
//        }
        // Create a notification channel (required for API 26+)
        createNotificationChannel(context);
        String reminderType = intent.getStringExtra("ReminderType");
        int plantId = intent.getIntExtra("PlantID", 0);
        PlantDataSource plantDataSource = new PlantDataSource(context);
        Plant plant = plantDataSource.getPlant(plantId);
        Log.d("TAG", "Reminder receiver " + reminderType);
        Intent notificationIntent = new Intent(context, HomeActivity.class);
        notificationIntent.putExtra("showDialog", true); // Pass data to show the dialog
        notificationIntent.putExtra("ReminderType", reminderType); // Pass data to show the dialog
        notificationIntent.putExtra("PlantID", plantId);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        /**Reminder Type
         * Fertilize
         * Watering
         * Sunlight
         * Change Soil
         * */

        assert reminderType != null;

        if (plantId != 0) {
            switch (reminderType) {
                case "Fertilize":
                    contentTitle = "Fertilize " + plant.getPlantName() + "!";
                    contentText = "It's time to fertilize " + plant.getPlantName() + " for healthy growth.";

                    largeIcon = R.drawable.fertilize;  // Larger icon for notification

                    notificationText = "Fertilization due! Don't forget to fertilize " + plant.getPlantName() + "!";
                    break;

                case "Watering":
                    contentTitle = "Water " + plant.getPlantName() + "!";
                    contentText = "It's time to water " + plant.getPlantName() + ". Keep it hydrated!";

                    largeIcon = R.drawable.watering_plants;  // Larger icon for notification

                    notificationText = "Watering time! " + plant.getPlantName() + " needs water.";
                    break;

                case "Sunlight":
                    contentTitle = "Give Sunlight to " + plant.getPlantName() + "!";
                    contentText = "Ensure " + plant.getPlantName() + " gets the required amount of sunlight.";

                    largeIcon = R.drawable.sunlight;  // Larger icon for notification

                    notificationText = "Sunlight needed! Move " + plant.getPlantName() + " to a sunnier spot.";
                    break;

                case "Change Soil":
                    contentTitle = "Change the Soil for " + plant.getPlantName() + "!";
                    contentText = "Refresh the soil for " + plant.getPlantName() + " for better growth.";

                    largeIcon = R.drawable.soil;  // Larger icon for notification

                    notificationText = "Soil change time! Give " + plant.getPlantName() + " new soil.";
                    break;

                default:
                    contentTitle = "Plant Reminder";
                    contentText = "Your plant needs attention.";
                    largeIcon = R.drawable.ic_plant;  // Fallback icon
                    notificationText = "Don't forget to take care of " + plant.getPlantName() + "!";
                    break;
            }
        } else {


            switch (reminderType) {
                case "Fertilize":
                    contentTitle = "Fertilize Your Plants!";
                    contentText = "It's time to fertilize your plants for healthy growth.";

                    largeIcon = R.drawable.fertilize;  // Larger icon for notification

                    notificationText = "Fertilization due! Don't forget to fertilize your plants!";
                    break;
                case "Watering":
                    contentTitle = "Water Your Plants!";
                    contentText = "It's time to water your plants. Keep them hydrated!";

                    largeIcon = R.drawable.watering_plants;  // Larger icon for notification

                    notificationText = "Watering time! Your plants need water.";
                    break;
                case "Sunlight":
                    contentTitle = "Provide Sunlight to Your Plants!";
                    contentText = "Ensure your plants get the required amount of sunlight.";

                    largeIcon = R.drawable.sunlight;  // Larger icon for notification

                    notificationText = "Sunlight needed! Move your plants to a sunnier spot.";
                    break;
                case "Change Soil":
                    contentTitle = "Change the Soil!";
                    contentText = "Your plants may need new soil for better growth.";

                    largeIcon = R.drawable.soil;  // Larger icon for notification

                    notificationText = "Soil change time! Refresh your plants' soil.";
                    break;
            }
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Intent dismissIntent = new Intent(context, NotificationDismissReceiver.class);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(context, 0, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);


        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.watering_plants) // Replace with your notification icon
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setSmallIcon(R.drawable.notification_plant)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), largeIcon))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notificationText)) // Description
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.check_mark, "Dismiss", dismissPendingIntent) // Dismiss button
                .addAction(R.drawable.search_end, "View", pendingIntent)
                .setAutoCancel(true);

        // Show the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }
    }

    private void createNotificationChannel(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Plant Watering Reminder",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Channel for Plant Watering Reminder");
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    private void showPopup(Context context) {
        // Make sure this is called only in MainActivity or the correct activity
        if (context instanceof HomeActivity) {
            HomeActivity mainActivity = (HomeActivity) context;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new AlertDialog.Builder(context)
                            .setTitle("Time to Water Plants!")
                            .setMessage("Don't forget to check your plant's water level and health!")
                            .setPositiveButton("Okay", null)
                            .show();
                }
            });
        }
    }

    // Check if the app is in the foreground
    private boolean isAppInForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            String currentApp = activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
            return currentApp.equals(context.getPackageName());
        }
        return false;
    }
}
