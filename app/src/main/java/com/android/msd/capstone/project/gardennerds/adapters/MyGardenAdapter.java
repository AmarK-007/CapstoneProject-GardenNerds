package com.android.msd.capstone.project.gardennerds.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.models.Garden;
import com.bumptech.glide.Glide;

import java.util.List;

public class MyGardenAdapter extends RecyclerView.Adapter<MyGardenAdapter.MyGardenViewHolder>{

    private final List<Garden> gardenList;

    public MyGardenAdapter(List<Garden> gardenList) {
        this.gardenList = gardenList;
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
        holder.gardenArea.setText("Area: " + garden.getArea() + " sq ft");

        // Use Glide or another library to load the garden image (lazy loading)
        Glide.with(holder.itemView.getContext())
                .load(garden.getImageUrl())  // replace with image URL if available
                .placeholder(R.drawable.new_garden_nerds)  // default image
                .into(holder.gardenImage);
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
