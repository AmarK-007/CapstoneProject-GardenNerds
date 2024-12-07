package com.android.msd.capstone.project.wear.gardennerds.utils;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android.msd.capstone.project.wear.gardennerds.R;
import com.android.msd.capstone.project.wear.gardennerds.models.Reminder;
import com.android.msd.capstone.project.wear.gardennerds.models.User;
import com.android.msd.capstone.project.wear.gardennerds.receiver.ReminderReceiver;

import java.util.Calendar;

public class Utility {

    /**
     * storeUser method
     *
     * @param context
     */
    //    storing employee inside shared preferences
//    public static void storeUser(User user, Context context) {
//
//        SharedPreferences sp = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("user", new Gson().toJson(user));
//        editor.commit();
//
//    }

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
     * Mann code
     */
    @SuppressLint("MissingPermission")
    public static void setSnoozeReminder(Context context, boolean isForSnooze, Reminder reminder) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, Constants.SET_REMINDER_SNOOZE); // Set reminder for 10 secs
        /** Amar code*/
       /* if (isForSnooze) {
            calendar.add(Calendar.SECOND, Constants.SET_REMINDER_SNOOZE); // Set reminder for 1 minute later (adjust as needed)
        } else {
            String[] time = reminder.getReminderTime().split(" ");
            String[] hourMinute = time[0].split(":");
            int hour = Integer.parseInt(hourMinute[0]);
            int minute = Integer.parseInt(hourMinute[1]);
            if (time[1].equalsIgnoreCase("PM") && hour != 12) {
                hour += 12;
            } else if (time[1].equalsIgnoreCase("AM") && hour == 12) {
                hour = 0;
            }
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            int frequency = Integer.parseInt(reminder.getFrequency());
            // Add the frequency in days to the current time
            calendar.add(Calendar.DAY_OF_YEAR, frequency);
        }*/
        /** Amar code ends*/

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
            /** Amar code*/
           /* if (isForSnooze) {
                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        pendingIntent
                );
            } else {
                int frequency = Integer.parseInt(reminder.getFrequency());
                long intervalMillis = frequency * 24 * 60 * 60 * 1000L; // Frequency in days converted to milliseconds
                alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        intervalMillis,
                        pendingIntent
                );
            }*/
            /** Amar code ends*/
        }
    }
}
