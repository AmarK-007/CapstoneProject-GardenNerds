package com.android.msd.capstone.project.gardennerds.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.fragments.GardenDetailFragment;
import com.android.msd.capstone.project.gardennerds.models.Garden;
import com.bumptech.glide.Glide;

import java.util.List;

public class MyGardenAdapter extends RecyclerView.Adapter<MyGardenAdapter.MyGardenViewHolder>{

    private final List<Garden> gardenList;
    private Context context;

    public MyGardenAdapter(List<Garden> gardenList, Context context) {
        this.gardenList = gardenList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyGardenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_item_garden, parent, false);
        return new MyGardenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyGardenViewHolder holder, int position) {
        Garden garden = gardenList.get(position);
        holder.gardenName.setText(garden.getName());
        holder.gardenArea.setText("Area: " + garden.getGardenArea() + " sq ft");

        // Use Glide or another library to load the garden image (lazy loading)
        Glide.with(holder.itemView.getContext())
                .load(garden.getImageUri())  // replace with image URL if available
                .placeholder(R.drawable.new_garden_nerds)  // default image
                .into(holder.gardenImage);

        // Handle item click
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("garden", garden); // Pass the garden object to the detail fragment
            GardenDetailFragment fragment = new GardenDetailFragment();
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
        return gardenList.size();
    }

    static class MyGardenViewHolder extends RecyclerView.ViewHolder {
        ImageView gardenImage;
        TextView gardenName;
        TextView gardenArea;

        MyGardenViewHolder(View itemView) {
            super(itemView);
            gardenImage = itemView.findViewById(R.id.imageViewGarden);
            gardenName = itemView.findViewById(R.id.textViewGardenName);
            gardenArea = itemView.findViewById(R.id.textViewGardenArea);
        }
    }
}
