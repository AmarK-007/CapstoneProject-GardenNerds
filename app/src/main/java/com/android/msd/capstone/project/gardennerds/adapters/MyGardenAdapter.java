package com.android.msd.capstone.project.gardennerds.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.LayoutItemGardenBinding;
import com.android.msd.capstone.project.gardennerds.fragments.GardenDetailFragment;
import com.android.msd.capstone.project.gardennerds.models.Garden;
import com.android.msd.capstone.project.gardennerds.viewmodels.GardenViewModel;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

public class MyGardenAdapter extends RecyclerView.Adapter<MyGardenAdapter.MyGardenViewHolder> {

    private List<Garden> gardenList;
    private Context context;

    private GardenViewModel gardenViewModel;

    public MyGardenAdapter(List<Garden> gardenList, Context context, GardenViewModel gardenViewModel) {
        this.gardenList = gardenList;
        this.context = context;
        this.gardenViewModel = gardenViewModel;
    }

    @NonNull
    @Override
    public MyGardenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate item layout using ViewBinding
        LayoutItemGardenBinding binding = LayoutItemGardenBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
                .placeholder(R.drawable.new_garden_nerds)  // default image
                .into(holder.binding.imageViewGarden);

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

        Log.e("Location", "Lat:" + getRoundedValue(garden.getGardenLatitude()) + "Long:" + getRoundedValue(garden.getGardenLongitude()));
        // Fetch weather data for the garden's location
        gardenViewModel.getWeatherData(getRoundedValue(garden.getGardenLatitude()), getRoundedValue(garden.getGardenLongitude()))
                .observeForever(weather -> {
                    if (weather != null) {
                        // Display temperature
                        holder.binding.textViewTemperature.setText(String.format(Locale.getDefault(), "%.1f°C", weather.getTemperature() - 273.15));

                        // Load weather icon using Glide
                        String iconUrl = "https://openweathermap.org/img/wn/" + weather.getIcon() + "@2x.png";
                        Glide.with(holder.itemView.getContext())
                                .load(iconUrl)
                                .into(holder.binding.imageViewWeather);
                    }
                });
    }

    private double getRoundedValue(String value){
        return Double.parseDouble(String.format("%.2f",Double.parseDouble(value)));
    }

    @Override
    public int getItemCount() {
        return gardenList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateDataSet(List<Garden> newGardenList) {
        this.gardenList.clear();
        this.gardenList = newGardenList;
        notifyDataSetChanged(); // Notify RecyclerView of data changes
    }

    public Garden getGardenAt(int position) {
        return gardenList.get(position);
    }

    static class MyGardenViewHolder extends RecyclerView.ViewHolder {

        private final LayoutItemGardenBinding binding;

        MyGardenViewHolder(LayoutItemGardenBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
