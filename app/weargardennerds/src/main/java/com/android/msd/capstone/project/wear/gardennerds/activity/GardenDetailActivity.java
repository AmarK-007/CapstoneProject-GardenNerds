package com.android.msd.capstone.project.wear.gardennerds.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.msd.capstone.project.wear.gardennerds.databinding.ActivityGardenDetailBinding;
import com.android.msd.capstone.project.wear.gardennerds.models.Garden;

public class GardenDetailActivity extends AppCompatActivity {

    private ActivityGardenDetailBinding detailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        detailBinding = ActivityGardenDetailBinding.inflate(getLayoutInflater());
        setContentView(detailBinding.getRoot());

        init();
    }

    private void init() {
        // Get the Garden object
        Garden garden = getIntent().getParcelableExtra("garden_object");

        if (garden != null) {
            detailBinding.textViewGardenName.setText(garden.getName());
            detailBinding.textViewDescription.setText(garden.getDescription());
            detailBinding.textViewSunlightPreference.setText("Sunlight Required: " + garden.getSunlightPreference());
            detailBinding.textViewWateringFrequency.setText("Watering Frequency: " + garden.getWateringFrequency() + "days");
            //detailBinding.textViewMoistureLevel.setText("Garden Area: " + garden.getGardenArea());
        }

        // Set the OnClickListener
        detailBinding.btnGoToPlants.setOnClickListener(v -> {
            // Create an Intent to start PlantListActivity
            Intent intent = new Intent(this, PlantListActivity.class);
            if (garden != null) {
                intent.putExtra("garden_object", garden);
            }

            // Start PlantListActivity
            startActivity(intent);
        });
    }
}