package com.android.msd.capstone.project.wear.gardennerds.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.wearable.view.WearableDialogHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.android.msd.capstone.project.wear.gardennerds.models.Plant;
import com.android.msd.capstone.project.wear.gardennerds.models.Reminder;
import com.android.msd.capstone.project.wear.gardennerds.receiver.DataReceiver;
import com.android.msd.capstone.project.wear.gardennerds.utils.DataRequestUtil;
import com.android.msd.capstone.project.wear.gardennerds.R;
import com.android.msd.capstone.project.wear.gardennerds.databinding.ActivityHomeBinding;
import com.android.msd.capstone.project.wear.gardennerds.databinding.CustomDialogLayoutBinding;
import com.android.msd.capstone.project.wear.gardennerds.utils.Utility;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding activityHomeBinding;

    private Dialog loginDialog;
    DataReceiver dataReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = activityHomeBinding.getRoot();
        setContentView(view);

        // Request user data from phone
       //requestUserDataFromPhone();

//        checkLoginStatus();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /**MAnn's code*/
        if (getIntent().getBooleanExtra("showDialog", false)) {
            Reminder reminder = getIntent().getParcelableExtra("ReminderInstance");
            if (reminder != null) {
                showReminderNotificationDialog(reminder);
            }
        }
    }

    /**
     * Mann's Code
     */
    private void showReminderNotificationDialog(Reminder reminder) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_reminder_popup_dialog_view, null);

        PlantDataSource plantDataSource = new PlantDataSource(this);
        Plant plant = plantDataSource.getPlant(reminder.getPlantId());

        // Initialize UI elements from the custom dialog layout
        Button snoozeBtn = dialogView.findViewById(R.id.ivSnooze);
        Button doneBtn = dialogView.findViewById(R.id.ivCheck);
        ImageView image = dialogView.findViewById(R.id.ivReminderType);
        TextView reminderTitle = dialogView.findViewById(R.id.tvReminderTitle);
        TextView reminderMessage = dialogView.findViewById(R.id.tvReminderMessage);

//        if (Objects.equals(reminderType, "Fertilizer")){
//            image.setImageResource(R.drawable.fertilize);
//        }
        String reminderTypeString = Utility.getReminderTypeString(this, reminder.getReminderTypeId());
        if (reminderTypeString != null)
            if (reminder.getPlantId() != 0) {
                switch (reminderTypeString) {
                    case "Fertilize":
                        reminderTitle.setText("Fertilize " + plant.getPlantName() + "!");
                        reminderMessage.setText("It's time to fertilize " + plant.getPlantName() + " for healthy growth.");
                        image.setImageResource(R.drawable.fertilize);
                        break;
                    case "Watering":
                        reminderTitle.setText("Water " + plant.getPlantName() + "!");
                        reminderMessage.setText("It's time to water " + plant.getPlantName() + ". Keep it hydrated!");
                        image.setImageResource(R.drawable.watering_plants);
                        break;
                    case "Sunlight":
                        reminderTitle.setText("Provide Sunlight to " + plant.getPlantName() + "!");
                        reminderMessage.setText("Ensure " + plant.getPlantName() + " gets the required amount of sunlight.");
                        image.setImageResource(R.drawable.sunlight);
                        break;
                    case "Change Soil":
                        reminderTitle.setText("Change the Soil for " + plant.getPlantName() + "!");
                        reminderMessage.setText(plant.getPlantName() + " may need new soil for better growth.");
                        image.setImageResource(R.drawable.soil);
                        break;
                    default:
                        reminderTitle.setText("Reminder for " + plant.getPlantName() + "!");
                        reminderMessage.setText("Don't forget to take care of " + plant.getPlantName() + "!");
                        image.setImageResource(R.drawable.ic_plant); // Use a generic image
                        break;
                }
            } else {
                switch (reminderTypeString) {
                    case "Fertilize":
                        reminderTitle.setText("Fertilize Your Plants!");
                        reminderMessage.setText("It's time to fertilize your plants for healthy growth.");
                        image.setImageResource(R.drawable.fertilize);
                        break;
                    case "Watering":
                        reminderTitle.setText("Water Your Plants!");
                        reminderMessage.setText("It's time to water your plants. Keep them hydrated!");
                        image.setImageResource(R.drawable.watering_plants);
                        break;
                    case "Sunlight":
                        reminderTitle.setText("Provide Sunlight to Your Plants!");
                        reminderMessage.setText("Ensure your plants get the required amount of sunlight.");
                        image.setImageResource(R.drawable.sunlight);
                        break;
                    case "Change Soil":
                        reminderTitle.setText("Change the Soil!");
                        reminderMessage.setText("Your plants may need new soil for better growth.");
                        image.setImageResource(R.drawable.soil);
                        break;
                }
            }

        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setCancelable(true);
        AlertDialog dialog = builder.create();
        // Handle button click
        doneBtn.setOnClickListener(v -> {
//            String inputText = inputEditText.getText().toString();
            // Do something with the input text
            Utility.setSnoozeReminder(this, false, reminder);
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
            // Dismiss the dialog
            dialog.dismiss();
        });

        snoozeBtn.setOnClickListener(v -> {
//            String inputText = inputEditText.getText().toString();
            // Do something with the input text
//            setSnoozeReminder(reminderType);
            Utility.setSnoozeReminder(this, true, reminder);
            Toast.makeText(this, "Snooze: Reminder set for 5 Minutes", Toast.LENGTH_SHORT).show();
            // Dismiss the dialog
            dialog.dismiss();
        });


        // Show the dialog
//        AlertDialog dialog = builder.create();
        dialog.show();
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

        // Register the receiver
         dataReceiver = new DataReceiver();
        IntentFilter filter = new IntentFilter("com.example.ACTION_SEND_DATA");
        registerReceiver(dataReceiver, filter, Context.RECEIVER_EXPORTED);
        // Check if the user is logged in
        if (!isLoggedIn()) {
            // Show the login dialog
            showLoginDialog();
            //DataRequestUtil.requestUserDataFromPhone(this, "login", "userName", "");
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
        // Unregister the receiver
        unregisterReceiver(dataReceiver);
        super.onDestroy();
    }
}