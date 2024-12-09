package com.android.msd.capstone.project.wear.gardennerds.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.wear.gardennerds.R;
import com.android.msd.capstone.project.wear.gardennerds.databinding.ListPlantBinding;
import com.android.msd.capstone.project.wear.gardennerds.models.Plant;
import com.bumptech.glide.Glide;

import java.util.List;


/**
 * Adapter for displaying a list of plants in a RecyclerView.
 */
public class PlantListAdapter extends RecyclerView.Adapter<PlantListAdapter.PlantViewHolder> {

    private final Context context;
    private List<Plant> plantList;

    // Constructor
    public PlantListAdapter(List<Plant> plantList, Context context) {
        this.context = context;
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListPlantBinding binding = ListPlantBinding.inflate(LayoutInflater.from(context), parent, false);
        return new PlantViewHolder(binding);
    }

    /**
     * Binds data to the ViewHolder at the specified position.
     * This method is called by RecyclerView to display the data at the specified position.
     */
    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Plant plant = plantList.get(position);

        // Bind data to the views
        holder.binding.tvPlantName.setText(plant.getPlantName());
        holder.binding.tvPlantSunlight.setText("Sunlight Required: " + plant.getSunlightLevel());
        holder.binding.tvPlantWatering.setText("Watering Frequency: " + plant.getWateringInterval() + "days");

        // Load plant image using Glide
        Glide.with(context)
                .load(plant.getImageUri()) // URI or URL for plant image
                .placeholder(R.drawable.ic_plant_default) // Placeholder image
                .into(holder.binding.ivPlantImage);

        // Handle item click
        holder.itemView.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    // ViewHolder class with View Binding
    public static class PlantViewHolder extends RecyclerView.ViewHolder {
        final ListPlantBinding binding;

        public PlantViewHolder(@NonNull ListPlantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
