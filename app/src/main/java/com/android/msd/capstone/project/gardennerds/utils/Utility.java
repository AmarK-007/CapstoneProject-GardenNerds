package com.android.msd.capstone.project.gardennerds.utils;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
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
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.broadcastReceivers.ReminderReceiver;
import com.android.msd.capstone.project.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.gardennerds.fragments.HomeFragment;
import com.android.msd.capstone.project.gardennerds.models.Plant;
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
 * Utility class
 */
public class Utility {

    private static final String TAG = Utility.class.getSimpleName();
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    private static final String SYMBOL_PATTERN = "[^a-z0-9 ]";
    private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    /**
     * validatePassword method
     *
     * @param password
     * @return
     */
    public static boolean validatePassword(String password) {
        return password.length() >= 5;
    }

    /**
     * validateIsPasswordEmpty method
     *
     * @param password
     * @return
     */
    public static boolean validateIsPasswordEmpty(String password) {
        return password.length() == 0;
    }

    /**
     * validateIsNameEmpty method
     *
     * @param name
     * @return
     */
    public static boolean validateIsNameEmpty(String name) {
        return name.length() == 0;
    }

    /**
     * validateIsTextAreaEmpty method
     *
     * @param textArea
     * @return
     */
    public static boolean validateIsTextAreaEmpty(String textArea) {
        return textArea.length() == 0;
    }

    /**
     * validateUserName method
     *
     * @param username
     * @return
     */
    public static boolean validateUserName(String username) {
        return username.length() > 3;
    }

    /**
     * validateSymbolsInUserName method
     *
     * @param username
     * @return
     */
    public static boolean validateSymbolsInUserName(String username) {
        Pattern p = Pattern.compile(SYMBOL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(username);
        return m.find();
    }

    /**
     * validateSpaceInUserName method
     *
     * @param username
     * @return
     */
    public static boolean validateSpaceInUserName(String username) {
        return username.contains(" ");
    }

    /**
     * validateEmail method
     *
     * @param email
     * @return
     */
    public static boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * getAppNameString method
     *
     * @param context
     * @return
     */
    public static String getAppNameString(Context context) {
        if (context != null)
            return context.getResources().getString(R.string.app_name);
        return "";
    }

    /**
     * capitalizeFirstLetter method
     *
     * @param input
     * @return
     */
    public static String capitalizeFirstLetter(final String input) {
        if (!Character.isUpperCase(input.charAt(0)))
            return Character.toUpperCase(input.charAt(0)) + input.substring(1);
        else return input;
    }

    /**
     * getImageResourceFromName method
     *
     * @param imageName
     * @param context
     * @return
     */
    public static int getImageResourceFromName(String imageName, Context context) {
        String imageNameWithoutExtension = imageName.substring(0, imageName.lastIndexOf('.'));
        int resourceId = context.getResources().getIdentifier(imageNameWithoutExtension, "drawable", context.getPackageName());

        return resourceId;
    }

    /**
     * storeUser method
     *
     * @param user
     * @param context
     */
    //    storing employee inside shared preferences
    public static void storeUser(User user, Context context) {

        SharedPreferences sp = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user", new Gson().toJson(user));
        editor.commit();

    }

    /**
     * getUser method
     *
     * @param context
     * @return
     */
    //    getting data from shared preferences
    public static User getUser(Context context) {
        SharedPreferences sp = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
//        Type listType = new TypeToken<ArrayList<Employee>>(){}.getType();
        Log.e("TAG", new Gson().fromJson(sp.getString("user", ""), User.class) + "");
        return new Gson().fromJson(sp.getString("user", ""), User.class);
    }

    /**
     * showStyledAlertDialog method
     *
     * @param context
     * @return
     */
    public static TextView showStyledAlertDialog(Context context) {
        TextView titleTextView = new TextView(context);
        titleTextView.setText(getAppNameString(context));
        titleTextView.setGravity(Gravity.LEFT);
        // add some leftpadding to the title matching the left padding of the message
        titleTextView.setPadding(60, 40, 0, 0);
        titleTextView.setMaxLines(1);
        titleTextView.setShadowLayer(20, 4, 3, ContextCompat.getColor(context, R.color.colorAccent));
        titleTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        titleTextView.setTypeface(null, Typeface.BOLD);
        return titleTextView;
    }

    /**
     * callHomeFragment method
     *
     * @param fragmentManager
     */
    public static void callHomeFragment(FragmentManager fragmentManager) {
        // Create a new instance of HomeFragment
        HomeFragment homeFragment = new HomeFragment();


        // Begin a FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the current fragment with HomeFragment
        fragmentTransaction.replace(R.id.frames, homeFragment);

        // Add this transaction to the back stack
        fragmentTransaction.addToBackStack(null);

        // Commit the transaction
        fragmentTransaction.commit();
    }

    /**
     * getSquareMeterToSquareFeet method
     *
     * @param squareMeter
     * @return
     */
    public static String getSquareMeterToSquareFeet(String squareMeter) {
        // Conversion factor from square meters to square feet
        return String.format(Locale.getDefault(), "%.2f", Double.parseDouble(squareMeter) * 10.7639);
    }

    /**
     * getReminderTypeString method
     *
     * @param context
     * @param reminderTypeId
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
     * getReminderTypeId method
     *
     * @param context
     * @param reminderTypeString
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
     * getDateTimeInSimpleDateFormat method
     *
     * @return String
     */
    public static String getDateTimeInSimpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * getCurrentDateTime method
     *
     * @return String
     */
    public static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * getDateFromString method
     *
     * @param dateStr
     * @return long
     */
    public static long getDateFromString(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(dateStr);
            return date.getTime();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * getDateInFormatFromString method
     *
     * @param dateStr
     * @return String
     */
    public static String getDateInFormatFromString(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatTo = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
        try {
            Date date = format.parse(dateStr);
            return formatTo.format(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     * getCurrentTime method
     *
     * @return String
     */
    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Mann code
     */
    @SuppressLint("MissingPermission")
    public static void setSnoozeReminder(String reminderType, int plantId, Context context) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 10); // Set reminder for 1 minute later (adjust as needed)

        Intent intent = new Intent(context, ReminderReceiver.class).putExtra("ReminderType", reminderType).putExtra("PlantID", plantId);
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

    public static int generateUniqueRequestCode(int plantId, int reminderType) {
        return plantId * 1000 + reminderType; // Combine plantId and reminderType to create a unique code
    }

    public static void setAlarmsForFrequency(Context context, int plantId, int frequency, int reminderType) {
        String reminderTypeString = Utility.getReminderTypeString(context, reminderType);
        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.SECOND, 30);

        PlantDataSource plantDataSource = new PlantDataSource(context);
        Plant plant = plantDataSource.getPlant(plantId);
        int uniqueRequestCode = generateUniqueRequestCode(plantId,reminderType);
        if (plant != null) {
            Log.d("Reminder", reminderTypeString + " Also plant name is " + plant.getPlantName() + "HAs reminder id =  "+reminderType + " Unique req code is = " + uniqueRequestCode);
            // Start time (e.g., 10 AM)

            /**to be uncommented*/
//


            LocalTime currentTime = LocalTime.now();

            // Add 2 minutes to the current time
            LocalTime newTime = currentTime.plusMinutes(2);

            // Extract the new hour and minute
            int newHour = newTime.getHour();
            int newMinute = newTime.getMinute();

            calendar.set(Calendar.HOUR_OF_DAY, newHour);  // 10 AM
            calendar.set(Calendar.MINUTE, newMinute);
            calendar.set(Calendar.SECOND, 0);

            /**up to here*/

            // Set the alarm

            setAlarm(context, calendar, uniqueRequestCode, reminderType, plantId,frequency);
        } else {
            Log.v(TAG, "setAlarmsForFrequency: Plant obj is null, hence ignored here.");
        }
    }

    // Helper method to set a single alarm
    public static void setAlarm(Context context, Calendar alarmTime, int uniqueCode, int reminderType, int plantId,int frequency) {
        Intent intent = new Intent(context, ReminderReceiver.class).putExtra("ReminderType", reminderType).putExtra("PlantID", plantId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, uniqueCode, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        Log.d("Recursion", alarmTime + "<time unique code>  " + uniqueCode + " remidID> " + reminderType + " plantId> "+ plantId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.e("DateTime" ,"Date" + sdf.format(alarmTime.getTime()));
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);


//        alarmTime.add(Calendar.DAY_OF_YEAR, -1);
        Log.e("DateTime" ,"Date" + sdf.format(alarmTime.getTime()));
        // Set the alarm to trigger at the exact time
        if (alarmManager != null) {
//            long intervalMillis = (long) frequency * 60 * 1000;
            long intervalMillis = frequency * 24 * 60 * 60 * 1000L;
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime.getTimeInMillis(), pendingIntent);
            // Set the repeating alarm
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,       // Wake up the device if it's asleep
                    alarmTime.getTimeInMillis(),   // Start time for the alarm
                    intervalMillis,                // Repeat interval (every frequency days)
                    pendingIntent                  // The PendingIntent to trigger the receiver
            );
        }
    }


    public static void cancelReminder(Context context, int reminderId, int plantId) {
        // Create the intent with the same reminder ID
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra("reminderId", reminderId);
        int uniqueRequestCode = generateUniqueRequestCode(plantId,reminderId);

        Log.d("DELETE REMINDER", reminderId + " unique code = " + uniqueRequestCode);

        // Pass the reminder ID if needed



        // Create the PendingIntent using the same reminder ID
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                uniqueRequestCode,  // Same request code (reminder ID)
                intent,
                PendingIntent.FLAG_IMMUTABLE  // Use FLAG_IMMUTABLE or FLAG_MUTABLE depending on your needs
        );

        // Get the AlarmManager system service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            // Cancel the alarm by passing the same PendingIntent
            alarmManager.cancel(pendingIntent);
        }
    }







    public static void setNavigationAndStatusBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.colorPrimary));
            window.setNavigationBarColor(activity.getResources().getColor(R.color.colorPrimary));
        }
    }

}
