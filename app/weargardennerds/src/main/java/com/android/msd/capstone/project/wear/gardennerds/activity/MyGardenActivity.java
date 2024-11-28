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
import com.android.msd.capstone.project.wear.gardennerds.models.Garden;

import java.util.ArrayList;

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

    private void init(){
        // Initialize adapter with an empty list
        myGardenBinding.wrcGardenList.setLayoutManager(new LinearLayoutManager(this));
        loadGarden();
        gardenAdapter = new GardenListAdapter(gardenList,this);
        myGardenBinding.wrcGardenList.setAdapter(gardenAdapter);

    }

    private void loadGarden() {
        // Add demo gardens (URL is null for placeholder image)
        gardenList.add(new Garden("Indoor Garden", "The garden is beautiful", "10"));
        gardenList.add(new Garden("Backyard Garden", "The garden is beautiful", "12"));
        gardenList.add(new Garden("Frontyard Garden", "The garden is beautiful", "11"));
        gardenList.add(new Garden("Balcony Garden", "The garden is beautiful", "15"));
    }
}