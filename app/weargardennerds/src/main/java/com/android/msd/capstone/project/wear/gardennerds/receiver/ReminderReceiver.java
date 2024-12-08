package com.android.msd.capstone.project.wear.gardennerds.receiver;


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
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.android.msd.capstone.project.wear.gardennerds.R;
import com.android.msd.capstone.project.wear.gardennerds.activity.HomeActivity;
import com.android.msd.capstone.project.wear.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.wear.gardennerds.models.Plant;
import com.android.msd.capstone.project.wear.gardennerds.models.Reminder;
import com.android.msd.capstone.project.wear.gardennerds.utils.Utility;

/**
 * BroadcastReceiver for handling plant reminder alarms.
 */
public class ReminderReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "PlantWateringReminder";
    private String contentTitle;
    private String contentText;
    private int largeIcon;
    private String notificationText;


    /**
     * Receives and handles the alarm broadcast.
     */
    @SuppressLint({"NotificationPermission", "UnsafeProtectedBroadcastReceiver"})
    @Override
    public void onReceive(Context context, Intent intent) {

// Create the notification channel (for Android Oreo and above)
        createNotificationChannel(context);
        // Get the reminder data from the intent
        Reminder reminder = intent.getParcelableExtra("ReminderInstance");
        if (reminder != null && reminder.getReminderId() != -1) {
            // Handle the alarm (e.g., show a toast or send a notification)
            Toast.makeText(context, "Reminder triggered! Reminder ID: " + reminder.getReminderId(), Toast.LENGTH_SHORT).show();

            /**Reminder Type
             * Fertilize
             * Watering
             * Sunlight
             * Change Soil
             * */
            String reminderTypeString = Utility.getReminderTypeString(context, reminder.getReminderTypeId());
            Log.d("TAG", "Reminder receiver " + reminderTypeString);

            // Get plant details from the database
            PlantDataSource plantDataSource = new PlantDataSource(context);
            Plant plant = plantDataSource.getPlant(reminder.getPlantId());

//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.MINUTE,1);
//        Log.d("Recursion", calendar + "<time unique code>  " + Utility.generateUniqueRequestCode(plantId,reminderId) + " remidID> " + reminderId + " plantId> "+ plantId);
//        Utility.setAlarm(context,calendar,Utility.generateUniqueRequestCode(plantId,reminderId),reminderId,plantId,1);

            Intent notificationIntent = new Intent(context, HomeActivity.class);
            notificationIntent.putExtra("showDialog", true); // Pass data to show the dialog
            notificationIntent.putExtra("ReminderInstance", reminder);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            // Set notification content based on reminder type and plant details
            if (reminderTypeString != null)
                if (reminder.getPlantId() != 0) {
                    switch (reminderTypeString) {
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
                    switch (reminderTypeString) {
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

            // Create a PendingIntent to launch the HomeActivity when the notification is clicked
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            // Generate a unique notification ID
            int notificationID = generateUniqueNotificationId(reminder.getPlantId(), reminderTypeString);

            // Create a PendingIntent for dismissing the notification
            Intent dismissIntent = new Intent(context, NotificationDismissReceiver.class).putExtra("notificationID", notificationID);
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
                notificationManager.notify(notificationID, builder.build());
            }
        }
    }

    private int generateUniqueNotificationId(int plantId, String reminderType) {
        return (plantId + reminderType.hashCode()) & 0x7FFFFFFF; // Ensure non-negative value
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
