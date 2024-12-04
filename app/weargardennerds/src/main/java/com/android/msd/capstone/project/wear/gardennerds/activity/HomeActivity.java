package com.android.msd.capstone.project.wear.gardennerds.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.wearable.view.WearableDialogHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.wear.activity.ConfirmationActivity;

import com.android.msd.capstone.project.wear.gardennerds.db.GardenDataSource;
import com.android.msd.capstone.project.wear.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.wear.gardennerds.db.ReminderDataSource;
import com.android.msd.capstone.project.wear.gardennerds.utils.DataRequestUtil;
import com.android.msd.capstone.project.wear.gardennerds.R;
import com.android.msd.capstone.project.wear.gardennerds.databinding.ActivityHomeBinding;
import com.android.msd.capstone.project.wear.gardennerds.databinding.CustomDialogLayoutBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding activityHomeBinding;

    private Dialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = activityHomeBinding.getRoot();
        setContentView(view);

        // Request user data from phone
       //requestUserDataFromPhone();

        checkLoginStatus();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void requestUserDataFromPhone() {
        // Request user data from phone
        DataRequestUtil.requestUserDataFromPhone(this, "login", "userName", "");
        // Request garden data from phone
        DataRequestUtil.requestUserDataFromPhone(this, "request", GardenDataSource.TABLE_NAME, "");
        // Request plant data from phone
        DataRequestUtil.requestUserDataFromPhone(this, "request", PlantDataSource.TABLE_NAME, "");
        // Request reminder data from phone
        DataRequestUtil.requestUserDataFromPhone(this, "request", ReminderDataSource.TABLE_NAME, "");

    }

    private boolean isLoggedIn() {
        // Check if the user is logged in
        SharedPreferences sharedPreferences = getSharedPreferences("Login_Username", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("userName", null);

        if (username == null) {
            // User is not logged in, show a popup dialog
            return false;
        } else {
            // User is logged in, proceed with the main activity
            return true;
        }
    }

    private void checkLoginStatus() {
        // Check if the user is logged in
        if (!isLoggedIn()) {
            // Show the login dialog
            showLoginDialog();
            DataRequestUtil.requestUserDataFromPhone(this, "login", "userName", "");
        } else {
            // User is logged in
            // Proceed with the app
        }
    }


    // Show a success activity
    public static void showSuccessActivity(Context context) {
        Intent intent = new Intent(context, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.SUCCESS_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, context.getString(R.string.msg_login_using_phone));
        context.startActivity(intent);
    }

    // Show a failure activity
    public static void showFailureActivity(Context context) {
        Intent intent = new Intent(context, ConfirmationActivity.class);
        intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.FAILURE_ANIMATION);
        intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, context.getString(R.string.msg_login_failed));
        context.startActivity(intent);
    }

    private void showLoginDialog() {
        CustomDialogLayoutBinding dialogBinding = CustomDialogLayoutBinding.inflate(getLayoutInflater());

        loginDialog = new WearableDialogHelper.DialogBuilder(this)
                .setView(dialogBinding.getRoot())
                .setCancelable(false)
                .create();
        //setcolor and layoutparams
        loginDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getColor(R.color.colorGrayLight)));
        loginDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loginDialog.show();

        dialogBinding.dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the app
                finish();
            }
        });
    }

    private void init() {

    }

    @Override
    protected void onDestroy() {
        if (loginDialog != null && loginDialog.isShowing()) {
            loginDialog.dismiss();
        }
        super.onDestroy();
    }
}