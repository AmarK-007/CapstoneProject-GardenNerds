package com.android.msd.capstone.project.wear.gardennerds.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.wear.gardennerds.R;
import com.android.msd.capstone.project.wear.gardennerds.activity.GardenDetailActivity;
import com.android.msd.capstone.project.wear.gardennerds.databinding.ListGardenBinding;
import com.android.msd.capstone.project.wear.gardennerds.models.Garden;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Adapter for displaying a list of gardens in a RecyclerView.
 */
public class GardenListAdapter extends RecyclerView.Adapter<GardenListAdapter.MyGardenViewHolder> {

    private List<Garden> gardenList;
    private Context context;

    /**
     * Constructor for the GardenListAdapter.

     */
    public GardenListAdapter(List<Garden> gardenList, Context context) {
        this.gardenList = gardenList;
        this.context = context;
    }


    /**
     * Creates and returns a new ViewHolder instance.
     * This method is called when the RecyclerView needs a new ViewHolder to represent an item.
     */
    @NonNull
    @Override
    public MyGardenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate item layout using ViewBinding
        ListGardenBinding binding = ListGardenBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyGardenViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull MyGardenViewHolder holder, int position) {
        Garden garden = gardenList.get(position);
        holder.binding.textViewGardenName.setText(garden.getName());
        holder.binding.textViewGardenArea.setText("Area: " + garden.getGardenArea() + " sq ft");

        // Use Glide or another library to load the garden image (lazy loading)
        Glide.with(context)
                .load(garden.getImageUri())  // replace with image URL if available
                .placeholder(R.drawable.ic_launcher_round)  // default image
                .into(holder.binding.imageViewGarden);

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, GardenDetailActivity.class);

            // Pass the Garden object
            intent.putExtra("garden_object", garden);

            // Start the GardenDetailActivity
            context.startActivity(intent);
        });
    }

    private double getRoundedValue(String value) {
        return Double.parseDouble(String.format("%.2f", Double.parseDouble(value)));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return gardenList.size();
    }

    /**
     * ViewHolder class for holding and displaying garden data.
     */
    static class MyGardenViewHolder extends RecyclerView.ViewHolder {

        private final ListGardenBinding binding;

        MyGardenViewHolder(ListGardenBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}