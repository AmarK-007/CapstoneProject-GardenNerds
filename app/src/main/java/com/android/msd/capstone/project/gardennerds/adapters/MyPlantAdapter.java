package com.android.msd.capstone.project.gardennerds.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.LayoutItemPlantBinding;
import com.android.msd.capstone.project.gardennerds.fragments.GardenDetailFragment;
import com.android.msd.capstone.project.gardennerds.fragments.PlantDetailFragment;
import com.android.msd.capstone.project.gardennerds.models.Plant;
import com.bumptech.glide.Glide;

import java.util.List;

public class MyPlantAdapter extends RecyclerView.Adapter<MyPlantAdapter.PlantViewHolder> {

    private final Context context;
    private List<Plant> plantList;

    // Constructor
    public MyPlantAdapter(List<Plant> plantList, Context context) {
        this.context = context;
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutItemPlantBinding binding = LayoutItemPlantBinding.inflate(LayoutInflater.from(context), parent, false);
        return new PlantViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Plant plant = plantList.get(position);

        // Bind data to the views
        holder.binding.tvPlantName.setText(plant.getPlantName());

        // Load plant image using Glide
        Glide.with(context)
                .load(plant.getImageUri()) // URI or URL for plant image
                .placeholder(R.drawable.ic_plant) // Placeholder image
                .into(holder.binding.ivPlantImage);

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("plant", plant); // Pass the garden object to the detail fragment
            PlantDetailFragment fragment = new PlantDetailFragment();
            fragment.setArguments(bundle);

            // Perform fragment transaction to open the detail page
            FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frames, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    public void updateDataset(List<Plant> newPlantsList) {
        this.plantList.clear();
        this.plantList = newPlantsList;
        notifyDataSetChanged(); // Notify RecyclerView of data changes
    }

    public Plant getPlantAt(int position) {
        return plantList.get(position);
    }

    // ViewHolder class
    // ViewHolder class with View Binding
    public static class PlantViewHolder extends RecyclerView.ViewHolder {
        final LayoutItemPlantBinding binding;

        public PlantViewHolder(@NonNull LayoutItemPlantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
