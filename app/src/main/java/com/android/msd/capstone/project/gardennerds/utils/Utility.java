package com.android.msd.capstone.project.gardennerds.utils;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.broadcastReceivers.ReminderReceiver;
import com.android.msd.capstone.project.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.gardennerds.fragments.HomeFragment;
import com.android.msd.capstone.project.gardennerds.models.Plant;
import com.android.msd.capstone.project.gardennerds.models.Reminder;
import com.android.msd.capstone.project.gardennerds.models.User;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class providing various helper methods for the application.
 */
public class Utility {

    private static final String TAG = Utility.class.getSimpleName();
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    private static final String SYMBOL_PATTERN = "[^a-z0-9 ]";
    private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    /**
     * Validates if the password meets the minimum length requirement.
     *
     * @param password The password to validate.
     * @return True if the password length is greater than or equal to 5, false otherwise.
     */
    public static boolean validatePassword(String password) {
        return password.length() >= 5;
    }

    /**
     * Checks if the password is empty.
     *
     * @param password The password to check.
     * @return True if the password is empty, false otherwise.
     */
    public static boolean validateIsPasswordEmpty(String password) {
        return password.length() == 0;
    }

    /**
     * Checks if the name is empty.
     *
     * @param name The name to check.
     * @return True if the name is empty, false otherwise.
     */
    public static boolean validateIsNameEmpty(String name) {
        return name.length() == 0;
    }

    /**
     * Checks if the text area is empty.
     *
     * @param textArea The text area to check.
     * @return True if the text area is empty, false otherwise.
     */
    public static boolean validateIsTextAreaEmpty(String textArea) {
        return textArea.length() == 0;
    }

    /**
     * Validates if the username meets the minimum length requirement.
     *
     * @param username The username to validate.
     * @return True if the username length is greater than 3, false otherwise.
     */
    public static boolean validateUserName(String username) {
        return username.length() > 3;
    }

    /**
     * Checks if the username contains any symbols.
     *
     * @param username The username to check.
     * @return True if the username contains symbols, false otherwise.
     */
    public static boolean validateSymbolsInUserName(String username) {
        Pattern p = Pattern.compile(SYMBOL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(username);
        return m.find();
    }

    /**
     * Checks if the username contains any spaces.
     *
     * @param username The username to check.
     * @return True if the username contains spaces, false otherwise.
     */
    public static boolean validateSpaceInUserName(String username) {
        return username.contains(" ");
    }

    /**
     * Validates if the email address is in the correct format.
     *
     * @param email The email address to validate.
     * @return True if the email address matches the pattern, false otherwise.
     */
    public static boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Validates if the time is in the correct HH:MM AM/PM format.
     *
     * @param time The time to validate.
     * @return True if the time matches the pattern, false otherwise.
     */
    public static boolean isValidTime(String time) {
        if (time == null || time.isEmpty()) {
            return false;
        }
        String timePattern = "^([1]?[0-2]|[0]?[1-9]):[0-5][0-9] ([AaPp][Mm])$";
        Pattern pattern = Pattern.compile(timePattern);
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }

    /**
     * Retrieves the application name string from the context.
     *
     * @param context The context to retrieve the application name from.
     * @return The application name string.
     */
    public static String getAppNameString(Context context) {
        if (context != null)
            return context.getResources().getString(R.string.app_name);
        return "";
    }

    /**
     * Capitalizes the first letter of the input string.
     *
     * @param input The input string.
     * @return The input string with the first letter capitalized.
     */
    public static String capitalizeFirstLetter(final String input) {
        if (!Character.isUpperCase(input.charAt(0)))
            return Character.toUpperCase(input.charAt(0)) + input.substring(1);
        else return input;
    }

    /**
     * Retrieves the image resource ID from the image name.
     *
     * @param imageName The name of the image.
     * @param context   The context to retrieve the resource from.
     * @return The image resource ID.
     */
    public static int getImageResourceFromName(String imageName, Context context) {
        String imageNameWithoutExtension = imageName.substring(0, imageName.lastIndexOf('.'));
        int resourceId = context.getResources().getIdentifier(imageNameWithoutExtension, "drawable", context.getPackageName());
        return resourceId;
    }

    /**
     * Stores the user object in shared preferences.
     *
     * @param user    The user object to store.
     * @param context The context to access shared preferences.
     */
    public static void storeUser(User user, Context context) {
        SharedPreferences sp = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user", new Gson().toJson(user));
        editor.commit();
    }

    /**
     * Retrieves the user object from shared preferences.
     *
     * @param context The context to access shared preferences.
     * @return The user object.
     */
    public static User getUser(Context context) {
        SharedPreferences sp = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        return new Gson().fromJson(sp.getString("user", ""), User.class);
    }

    /**
     * Displays a styled alert dialog with the application name as the title.
     *
     * @param context The context to create the alert dialog.
     * @return The TextView containing the styled title.
     */
    public static TextView showStyledAlertDialog(Context context) {
        TextView titleTextView = new TextView(context);
        titleTextView.setText(getAppNameString(context));
        titleTextView.setGravity(Gravity.LEFT);
        titleTextView.setPadding(60, 40, 0, 0);
        titleTextView.setMaxLines(1);
        titleTextView.setShadowLayer(20, 4, 3, ContextCompat.getColor(context, R.color.colorAccent));
        titleTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        titleTextView.setTypeface(null, Typeface.BOLD);
        return titleTextView;
    }

    /**
     * Replaces the current fragment with the HomeFragment.
     *
     * @param fragmentManager The FragmentManager to perform the transaction.
     */
    public static void callHomeFragment(FragmentManager fragmentManager) {
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frames, homeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * Converts square meters to square feet.
     *
     * @param squareMeter The value in square meters.
     * @return The value in square feet formatted to two decimal places.
     */
    public static String getSquareMeterToSquareFeet(String squareMeter) {
        return String.format(Locale.getDefault(), "%.2f", Double.parseDouble(squareMeter) * 10.7639);
    }

    /**
     * Retrieves the reminder type string based on the reminder type ID.
     *
     * @param context         The context to retrieve the string resource.
     * @param reminderTypeId  The reminder type ID.
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
     * Retrieves the reminder type ID based on the reminder type string.
     *
     * @param context              The context to retrieve the string resource.
     * @param reminderTypeString   The reminder type string.
     * @return The reminder type ID.
     */
    public static int getReminderTypeId(Context context, String reminderTypeString) {
        if (reminderTypeString.equals(context.getString(R.string.text_reminder_type_watering))) {
            return Constants.REMINDER_TYPE_WATER;
        } else if (reminderTypeString.equals(context.getString(R.string.text_reminder_type_fertilize))) {
            return Constants.REMINDER_TYPE_FERTILIZE;
        } else if (reminderTypeString.equals(context.getString(R.string.text_reminder_type_sunlight))) {
            return Constants.REMINDER_TYPE_SUNLIGHT;
        } else if (reminderTypeString.equals(context.getString(R.string.text_reminder_type_changeSoil))) {
            return Constants.REMINDER_TYPE_CHANGE_SOIL;
        } else {
            return -1;
        }
    }

    /**
     * Retrieves the current date and time in a simple date format.
     *
     * @return The current date and time as a string.
     */
    public static String getDateTimeInSimpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * Retrieves the current date and time.
     *
     * @return The current date and time as a string.
     */
    public static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Converts a date string to a timestamp.
     *
     * @param dateStr The date string to convert.
     * @return The timestamp in milliseconds.
     */
    public static long getDateFromString(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dateStr);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Converts a date string to a formatted date string.
     *
     * @param dateStr The date string to convert.
     * @return The formatted date string.
     */
    public static String getDateInFormatFromString(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatTo = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
        try {
            Date date = format.parse(dateStr);
            return formatTo.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Retrieves the current time.
     *
     * @return The current time as a string.
     */
    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Sets a snooze reminder.
     *
     * @param context     The context to access system services.
     * @param isForSnooze Indicates if the reminder is for snooze.
     * @param reminder    The reminder object.
     */
    @SuppressLint("MissingPermission")
    public static void setSnoozeReminder(Context context, boolean isForSnooze, Reminder reminder) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, Constants.SET_REMINDER_SNOOZE);

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

    /**
     * Generates a unique request code based on plant ID and reminder type.
     *
     * @param plantId      The plant ID.
     * @param reminderType The reminder type.
     * @return The unique request code.
     */
    public static int generateUniqueRequestCode(int plantId, int reminderType) {
        return plantId * 1000 + reminderType;
    }

    /**
     * Sets alarms for a specific frequency.
     *
     * @param context       The context to access system services.
     * @param plantId       The plant ID.
     * @param frequency     The frequency in days.
     * @param reminderType  The reminder type.
     */
    public static void setAlarmsForFrequency(Context context, int plantId, int frequency, int reminderType) {
        String reminderTypeString = Utility.getReminderTypeString(context, reminderType);
        Calendar calendar = Calendar.getInstance();

        PlantDataSource plantDataSource = new PlantDataSource(context);
        Plant plant = plantDataSource.getPlant(plantId);
        int uniqueRequestCode = generateUniqueRequestCode(plantId, reminderType);
        if (plant != null) {
            Log.d(TAG, "setAlarmsForFrequency: " + reminderTypeString + " Also plant name is " + plant.getPlantName() + "HAs reminder id =  " + reminderType + " Unique req code is = " + uniqueRequestCode);

            LocalTime currentTime = LocalTime.now();
            LocalTime newTime = currentTime.plusMinutes(2);
            int newHour = newTime.getHour();
            int newMinute = newTime.getMinute();

            calendar.set(Calendar.HOUR_OF_DAY, newHour);
            calendar.set(Calendar.MINUTE, newMinute);
            calendar.set(Calendar.SECOND, 0);

            setAlarm(context, calendar, uniqueRequestCode, reminderType, plantId, frequency);
        } else {
            Log.v(TAG, "setAlarmsForFrequency: Plant obj is null, hence ignored here.");
        }
    }

    /**
     * Helper method to set a single alarm.
     *
     * @param context      The context to access system services.
     * @param alarmTime    The time to set the alarm.
     * @param uniqueCode   The unique request code.
     * @param reminderType The reminder type.
     * @param plantId      The plant ID.
     * @param frequency    The frequency in days.
     */
    public static void setAlarm(Context context, Calendar alarmTime, int uniqueCode, int reminderType, int plantId, int frequency) {
        Intent intent = new Intent(context, ReminderReceiver.class).putExtra("ReminderType", reminderType).putExtra("PlantID", plantId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, uniqueCode, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d("Recursion", alarmTime + "<time unique code>  " + uniqueCode + " remidID> " + reminderType + " plantId> " + plantId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.e("DateTime", "Date" + sdf.format(alarmTime.getTime()));
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        if (alarmManager != null) {
            long intervalMillis = frequency * 24 * 60 * 60 * 1000L;
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    alarmTime.getTimeInMillis(),
                    intervalMillis,
                    pendingIntent
            );
        }
    }

    /**
     * Cancels a reminder.
     *
     * @param context     The context to access system services.
     * @param reminderId  The reminder ID.
     * @param plantId     The plant ID.
     */
    public static void cancelReminder(Context context, int reminderId, int plantId) {
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra("reminderId", reminderId);
        int uniqueRequestCode = generateUniqueRequestCode(plantId, reminderId);

        Log.d("DELETE REMINDER", reminderId + " unique code = " + uniqueRequestCode);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                uniqueRequestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
    /**
     * Shows a time picker dialog and sets the selected time to the provided EditText.
     *
     * @param context  the context in which the dialog should be shown
     * @param editText the EditText to set the selected time
     */
    public static void showTimePickerDialog(Context context, EditText editText) {
        // Get the current time as the default values for the picker
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view, hourOfDay, minuteOfHour) -> {
            // Convert 24-hour format to 12-hour format with AM/PM
            String amPm = (hourOfDay >= 12) ? "PM" : "AM";
            int hourIn12Format = (hourOfDay > 12) ? hourOfDay - 12 : (hourOfDay == 0 ? 12 : hourOfDay);
            String time = String.format("%02d:%02d %s", hourIn12Format, minuteOfHour, amPm);
            editText.setText(time);
        }, hour, minute, false); // Use 12-hour format

        timePickerDialog.show();
    }

    /**
     * Sets the navigation and status bar color for the provided activity.
     *
     * @param activity the activity for which the colors should be set
     */
    public static void setNavigationAndStatusBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.colorPrimary));
            window.setNavigationBarColor(activity.getResources().getColor(R.color.colorPrimary));
        }
    }

    /**
     * Shows a toast message.
     *
     * @param context the context in which the toast should be shown
     * @param message the message to be displayed in the toast
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
