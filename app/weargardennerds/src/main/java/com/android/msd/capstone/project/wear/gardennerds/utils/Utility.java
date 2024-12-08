package com.android.msd.capstone.project.wear.gardennerds.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.msd.capstone.project.wear.gardennerds.models.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.Set;

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


    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isLoggedIn(Context context) {
        // Check if the user is logged in
        SharedPreferences sharedPreferences = context.getSharedPreferences("Login_Username", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("userName", null);

        if (username == null) {
            // User is not logged in, show a popup dialog
            return false;
        } else {
            // User is logged in, proceed with the main activity
            return true;
        }
    }
}
