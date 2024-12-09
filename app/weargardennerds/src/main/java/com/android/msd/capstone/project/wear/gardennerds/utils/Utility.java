package com.android.msd.capstone.project.wear.gardennerds.utils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.msd.capstone.project.wear.gardennerds.R;
import com.android.msd.capstone.project.wear.gardennerds.models.Reminder;
import com.android.msd.capstone.project.wear.gardennerds.receiver.ReminderReceiver;

import java.util.Calendar;

public class Utility {

    /**
     * Displays a toast message.
     *
     * @param context The context in which the toast should be shown.
     * @param message The message to be displayed in the toast.
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Checks if the user is logged in.
     *
     * @param context The context to access shared preferences.
     * @return True if the user is logged in, false otherwise.
     */
    public static boolean isLoggedIn(Context context) {
        // Check if the user is logged in
        String username = WearDataSyncUtil.readUserDataFromPreference(context);

        if (username == null) {
            // User is not logged in, show a popup dialog
            return false;
        } else {
            // User is logged in, proceed with the main activity
            return true;
        }
    }

    /**
     * Returns the reminder type string based on the reminder type ID.
     *
     * @param context The context to access resources.
     * @param reminderTypeId The ID of the reminder type.
     * @return The reminder type string.
     */
    public static String getReminderTypeString(Context context, int reminderTypeId) {
        switch (reminderTypeId) {
            case Constants.REMINDER_TYPE_WATER:
                return context.getString(R.string.text_reminder_type_watering);
            case Constants.REMINDER_TYPE_FERTILIZE:
                return context.getString(R.string.text_reminder_type_fertilize);
            case Constants.REMINDER_TYPE_SUNLIGHT:
                return context.getString(R.string.text_reminder_type_sunlight);
            case Constants.REMINDER_TYPE_CHANGE_SOIL:
                return context.getString(R.string.text_reminder_type_changeSoil);
            default:
                return "UNKNOWN";
        }
    }

    /**
     * Sets a snooze reminder.
     *
     * @param context The context to access system services.
     * @param isForSnooze Indicates if the reminder is for snooze.
     * @param reminder The reminder object containing reminder details.
     */
    @SuppressLint("MissingPermission")
    public static void setSnoozeReminder(Context context, boolean isForSnooze, Reminder reminder) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, Constants.SET_REMINDER_SNOOZE); // Set reminder for 10 secs

        Intent intent = new Intent(context, ReminderReceiver.class).putExtra("ReminderInstance", reminder);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        }
    }
}