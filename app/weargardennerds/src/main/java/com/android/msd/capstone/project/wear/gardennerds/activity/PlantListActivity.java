package com.android.msd.capstone.project.wear.gardennerds.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.msd.capstone.project.wear.gardennerds.adapters.GardenListAdapter;
import com.android.msd.capstone.project.wear.gardennerds.adapters.PlantListAdapter;
import com.android.msd.capstone.project.wear.gardennerds.databinding.ActivityPlantListBinding;
import com.android.msd.capstone.project.wear.gardennerds.db.GardenDataSource;
import com.android.msd.capstone.project.wear.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.wear.gardennerds.models.Garden;
import com.android.msd.capstone.project.wear.gardennerds.models.Plant;

import java.util.ArrayList;

public class PlantListActivity extends AppCompatActivity {

    private ActivityPlantListBinding plantListBinding;
    private PlantListAdapter plantAdapter;
    private ArrayList<Plant> plantList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        plantListBinding = ActivityPlantListBinding.inflate(getLayoutInflater());
        setContentView(plantListBinding.getRoot());

        init();
    }

    private void init() {
        // Initialize adapter with an empty list
        plantListBinding.wrcPlantList.setLayoutManager(new LinearLayoutManager(this));
        loadPlants();
        plantAdapter = new PlantListAdapter(plantList, this);
        plantListBinding.wrcPlantList.setAdapter(plantAdapter);
    }

    private void loadPlants() {
        PlantDataSource gardenDataSource = new PlantDataSource(this);
        plantList.clear();
        plantList.addAll(gardenDataSource.getAllPlants());
        if (plantList == null || plantList.isEmpty()) {
            addDemoPlants();
        }
    }

    private void addDemoPlants() {
        // Add demo plants (URL is null for placeholder image)
        plantList.add(new Plant("Sunflower"));
        plantList.add(new Plant("Dalia"));
        plantList.add(new Plant("Tulips"));

    }
}