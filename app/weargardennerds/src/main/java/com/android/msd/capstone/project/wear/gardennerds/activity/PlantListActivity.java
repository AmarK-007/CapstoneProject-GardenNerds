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
        //receive garden_object from intent
        Garden garden = getIntent().getParcelableExtra("garden_object");


        // Initialize adapter with an empty list
        plantListBinding.wrcPlantList.setLayoutManager(new LinearLayoutManager(this));
        loadPlants(garden.getGardenId());
        plantAdapter = new PlantListAdapter(plantList, this);
        plantListBinding.wrcPlantList.setAdapter(plantAdapter);
    }

    private void loadPlants(int gardenId) {
        PlantDataSource gardenDataSource = new PlantDataSource(this);
        plantList.clear();
        if (gardenId > 0) {
            plantList.addAll(gardenDataSource.getPlantsByGardenId(gardenId));
            if (plantList == null || plantList.isEmpty()) {
                addDemoPlants();
            }
        } else {
            addDemoPlants();
        }
    }

    private void addDemoPlants() {
        // Add default values
        plantList.add(new Plant("Red Anthrium", "shady", "6"));
        plantList.add(new Plant("Jade plant", "shady", "6"));
        //plantList.add(new Plant("Tulips"));

    }
}