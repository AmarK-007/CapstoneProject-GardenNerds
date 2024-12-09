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

/**
 * Activity to display a list of plants.
 */
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

    /**
     * Initializes the activity by setting up the RecyclerView and loading plant data.
     * This method sets up the LayoutManager for the RecyclerView, loads plant data from the database,
     * and creates the PlantListAdapter to display the data.
     */
    private void init() {
        // Initialize adapter with an empty list
        plantListBinding.wrcPlantList.setLayoutManager(new LinearLayoutManager(this));
        loadPlants();
        plantAdapter = new PlantListAdapter(plantList, this);
        plantListBinding.wrcPlantList.setAdapter(plantAdapter);
    }


    /**
     * Loads plant data from the database.
     * This method retrieves all plants from the PlantDataSource and adds them to the plantList.
     * If no plants are found, it adds demo plants to the list.
     */
    private void loadPlants() {
        PlantDataSource gardenDataSource = new PlantDataSource(this);
        plantList.clear();
        plantList.addAll(gardenDataSource.getAllPlants());
        if (plantList == null || plantList.isEmpty()) {
            addDemoPlants();
        }
    }

    /**
     * Adds demo plants to the plant list.
     * This method is called when no plants are found in the database.
     * It adds pre-defined demo plants to the list for display.
     */
    private void addDemoPlants() {
        // Add default values
        plantList.add(new Plant("Red Anthrium","shady","6"));
        plantList.add(new Plant("Indoor Orchid","shady","6"));
        //plantList.add(new Plant("Tulips"));

    }
}