package com.android.msd.capstone.project.gardennerds.broadcastReceivers.newReminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.activity.HomeActivity;
import com.android.msd.capstone.project.gardennerds.broadcastReceivers.NotificationDismissReceiver;
import com.android.msd.capstone.project.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.gardennerds.db.ReminderDataSource;
import com.android.msd.capstone.project.gardennerds.models.Plant;
import com.android.msd.capstone.project.gardennerds.models.Reminder;
import com.android.msd.capstone.project.gardennerds.utils.Utility;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "reminder_channel";
    private static int notificationID = 0;
    private String contentTitle;
    private String contentText;
    private int largeIcon;
    private String notificationText;

    @Override
    public void onReceive(Context context, Intent intent) {


        Reminder reminder = intent.getParcelableExtra("ReminderInstance");
        sendNotification(context, reminder);
        // Optionally, reschedule the reminder
        ReminderManager reminderManager = new ReminderManager(context);
        reminderManager.startReminder(reminder.getReminderId()); // Reschedule for next day at 9 AM
    }

    // Send notification
    private void sendNotification(Context context, Reminder reminder) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, HomeActivity.class);
        notificationIntent.putExtra("showDialog", true); // Pass data to show the dialog
        notificationIntent.putExtra("ReminderInstance", reminder);
        PlantDataSource plantDataSource = new PlantDataSource(context);
        Plant plant = plantDataSource.getPlant(reminder.getPlantId());
        String reminderType = Utility.getReminderTypeString(context, reminder.getReminderTypeId());
        setNotificationData(plant, reminderType);

        Log.d(this.toString(), "Reminder type= " + reminderType + "Plant type= " + plant.getPlantName());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Reminder Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );


        Intent dismissIntent = new Intent(context, NotificationDismissReceiver.class).putExtra("notificationID", notificationID);
        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(context, 0, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);


        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
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
                .setAutoCancel(true)
                .build();

        notificationManager.notify(reminder.getReminderId(), notification);
    }

    private void setNotificationData(Plant plant, String reminderTypeString) {
        if (plant.getPlantId() != 0) {
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
    }
}
