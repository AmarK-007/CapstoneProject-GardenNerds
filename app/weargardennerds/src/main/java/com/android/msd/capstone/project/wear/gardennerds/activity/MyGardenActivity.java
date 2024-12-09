package com.android.msd.capstone.project.wear.gardennerds.activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.msd.capstone.project.wear.gardennerds.R;
import com.android.msd.capstone.project.wear.gardennerds.adapters.GardenListAdapter;
import com.android.msd.capstone.project.wear.gardennerds.databinding.ActivityHomeBinding;
import com.android.msd.capstone.project.wear.gardennerds.databinding.ActivityMyGardenBinding;
import com.android.msd.capstone.project.wear.gardennerds.db.GardenDataSource;
import com.android.msd.capstone.project.wear.gardennerds.models.Garden;

import java.util.ArrayList;

/**
 * Activity to display the user's gardens.
 */
public class MyGardenActivity extends AppCompatActivity {

    private ActivityMyGardenBinding myGardenBinding;
    private GardenListAdapter gardenAdapter;
    private ArrayList<Garden> gardenList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myGardenBinding = ActivityMyGardenBinding.inflate(getLayoutInflater());
        setContentView(myGardenBinding.getRoot());

        init();
    }

    /**
     * Initializes the activity by setting up the RecyclerView and loading garden data.
     * This method sets up the LayoutManager for the RecyclerView, loads garden data from the database,
     * and creates the GardenListAdapter to display the data.
     */
    private void init() {
        // Initialize adapter with an empty list
        myGardenBinding.wrcGardenList.setLayoutManager(new LinearLayoutManager(this));
        loadGarden();
        gardenAdapter = new GardenListAdapter(gardenList, this);
        myGardenBinding.wrcGardenList.setAdapter(gardenAdapter);

    }

    /**
     * Loads garden data from the database.
     * This method retrieves all gardens from the GardenDataSource and adds them to the gardenList.
     * If no gardens are found, it adds demo gardens to the list.
     */
    private void loadGarden() {

        GardenDataSource gardenDataSource = new GardenDataSource(this);
        gardenList.clear();
        gardenList.addAll(gardenDataSource.getAllGardens());
        if (gardenList == null || gardenList.isEmpty()) {
            addDemoGardens();
        }
    }

    /**
     * Adds demo gardens to the garden list.
     * This method is called when no gardens are found in the database.
     * It adds pre-defined demo gardens to the list for display.
     */
    private void addDemoGardens() {
        // Add demo gardens (URL is null for placeholder image)
        gardenList.add(new Garden("Indoor Garden", "This is my Indoor Garden", "5", "50%", "6"));
        gardenList.add(new Garden("Outdoor Garden", "This is my Outdoor Garden", "20", "50%", "6"));
        gardenList.add(new Garden("Back yard Garden", "The is my Back yard Garden", "30", "50%", "6"));
        gardenList.add(new Garden("Balcony Garden", "The is my Balcony Garden", "10", "50%", "6"));
    }
}