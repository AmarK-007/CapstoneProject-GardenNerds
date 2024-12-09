package com.android.msd.capstone.project.wear.gardennerds.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.wearable.view.WearableDialogHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.wear.activity.ConfirmationActivity;

import com.android.msd.capstone.project.wear.gardennerds.databinding.CustomReminderPopupDialogViewBinding;
import com.android.msd.capstone.project.wear.gardennerds.db.GardenDataSource;
import com.android.msd.capstone.project.wear.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.wear.gardennerds.models.Plant;
import com.android.msd.capstone.project.wear.gardennerds.models.Reminder;
import com.android.msd.capstone.project.wear.gardennerds.utils.WearDataSyncUtil;
import com.android.msd.capstone.project.wear.gardennerds.R;
import com.android.msd.capstone.project.wear.gardennerds.databinding.ActivityHomeBinding;
import com.android.msd.capstone.project.wear.gardennerds.databinding.CustomDialogLayoutBinding;
import com.android.msd.capstone.project.wear.gardennerds.utils.Utility;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

import java.util.Arrays;

/**
 * Main activity of the Wear OS app, displaying the home screen.
 */
public class HomeActivity extends AppCompatActivity implements MessageClient.OnMessageReceivedListener {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private ActivityHomeBinding activityHomeBinding;

    private Dialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        activityHomeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = activityHomeBinding.getRoot();
        setContentView(view);

        init();

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

    /**
     * Shows a dialog for reminder notifications.
     * This method creates and displays a dialog with reminder details,
     * allowing the user to snooze or mark the reminder as done.
     */
    private void showReminderNotificationDialog(Reminder reminder) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CustomReminderPopupDialogViewBinding dialogViewBinding = CustomReminderPopupDialogViewBinding.inflate(getLayoutInflater());
//        View dialogView = inflater.inflate(R.layout.custom_reminder_popup_dialog_view, null);

        PlantDataSource plantDataSource = new PlantDataSource(this);
        Plant plant = plantDataSource.getPlant(reminder.getPlantId());

        // Initialize UI elements from the custom dialog layout
        ImageView snoozeBtn = dialogViewBinding.ivSnooze;
        ImageView doneBtn = dialogViewBinding.ivCheck;
        ImageView image = dialogViewBinding.ivReminderType;
        TextView reminderTitle = dialogViewBinding.tvReminderTitle;
        TextView reminderMessage = dialogViewBinding.tvReminderMessage;

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
        builder.setView(dialogViewBinding.getRoot())
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

    /**
     * Initializes the activity by setting up message listener and checking login status.
     * This method registers the activity as a listener for messages from the Wearable API
     * and checks if the user is logged in.
     */
    private void init() {
        Wearable.getMessageClient(this).addListener(this);
        checkLoginStatus();
    }

    @Override
    protected void onDestroy() {
        if (loginDialog != null && loginDialog.isShowing()) {
            loginDialog.dismiss();
        }
        // Unregister the receiver
        super.onDestroy();
    }


    /**
     * Called when a message is received from the Wearable API.
     * Handles data updates received from the phone.
     *
     */
    @Override
    public void onMessageReceived(@NonNull MessageEvent messageEvent) {
        if (messageEvent.getPath().equals(WearDataSyncUtil.UPDATE_DATA_PATH)) {
            Utility.showToast(getBaseContext(), "In onMessageReceived");
            byte[] bytes = messageEvent.getData();
            if (bytes != null) {
                Log.v(TAG, "Message from Phone Recieved. :::: " + Arrays.toString(bytes));
                String dataReceived = Arrays.toString(bytes);
                if (dataReceived != null && !dataReceived.isEmpty()) {
                    WearDataSyncUtil.handleDataRequest(this, dataReceived);
                } else {
                    Log.e(TAG, "Data received is null or empty");
                }
            }

        }
    }

    /**
     * Requests user data from the connected phone.
     * This method initiates a data request to the phone to retrieve user data.
     */
    private void requestUserDataFromPhone() {
        WearDataSyncUtil.findCapabilityClient(this);
    }

    /**
     * Checks the login status of the user.
     * If the user is not logged in, shows a login dialog and requests user data from the phone.
     * If the user is logged in, proceeds with the app.
     */
    private void checkLoginStatus() {

        // Check if the user is logged in
        if (!Utility.isLoggedIn(this)) {
            // Show the login dialog
            //showLoginDialog();
            // Request user data from phone
            requestUserDataFromPhone();
        } else {
            // User is logged in
            // Proceed with the app
        }

        activityHomeBinding.btnMyGarden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(HomeActivity.this, MyGardenActivity.class);
                startActivity(mIntent);
            }
        });

        activityHomeBinding.btnReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(HomeActivity.this, ReminderListActivity.class);
                startActivity(mIntent);
            }
        });
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

    /**
     * Shows a dialog prompting the user to log in using their phone.
     * This method creates and displays a dialog informing the user that they need to log in
     * using their phone and provides a button to dismiss the dialog.
     */
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
                //finish();
                loginDialog.dismiss();
            }
        });
    }


}