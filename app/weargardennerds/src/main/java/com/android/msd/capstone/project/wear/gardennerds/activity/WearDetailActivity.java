package com.android.msd.capstone.project.wear.gardennerds.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

import com.android.msd.capstone.project.wear.gardennerds.R;
import com.android.msd.capstone.project.wear.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.wear.gardennerds.models.Plant;
import com.android.msd.capstone.project.wear.gardennerds.models.Reminder;
import com.android.msd.capstone.project.wear.gardennerds.utils.Utility;


public class WearDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wear_detail);
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

}