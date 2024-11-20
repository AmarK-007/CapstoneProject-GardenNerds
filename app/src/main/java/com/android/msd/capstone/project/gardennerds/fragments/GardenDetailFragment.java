package com.android.msd.capstone.project.gardennerds.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.adapters.MyPlantAdapter;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentGardenDetailBinding;
import com.android.msd.capstone.project.gardennerds.models.Garden;
import com.android.msd.capstone.project.gardennerds.models.Plant;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GardenDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GardenDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentGardenDetailBinding gardenDetailBinding;

    public GardenDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GardenDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GardenDetailFragment newInstance(String param1, String param2) {
        GardenDetailFragment fragment = new GardenDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gardenDetailBinding = FragmentGardenDetailBinding.inflate(inflater, container, false);

        init();

        return gardenDetailBinding.getRoot();
    }

    private void init() {
        // Retrieve the passed garden object
        if (getArguments() != null) {
            Garden garden = (Garden) getArguments().getSerializable("garden");

            // Set data to the views
            gardenDetailBinding.textViewGardenName.setText(garden.getName());
            gardenDetailBinding.textViewDescription.setText(garden.getDescription());
            gardenDetailBinding.textViewSunlightPreference.setText("Sunlight Required: "+garden.getSunlightPreference());
            gardenDetailBinding.textViewWateringFrequency.setText("Watering Frequency: "+garden.getWateringFrequency()+"days");
            gardenDetailBinding.textViewMoistureLevel.setText("Garden Area: "+garden.getGardenArea());

            // Use image loading library like Glide to load the image
            Glide.with(requireActivity())
                    .load(garden.getImageUri())  // replace with image URL if available
                    .placeholder(R.drawable.new_garden_nerds)  // default image
                    .into(gardenDetailBinding.imageViewGarden);
        }

        gardenDetailBinding.fabAddPlant.setOnClickListener(v -> {
            // Replace the current fragment with AddGardenFragment
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frames, new AddPlantFragment())
                    .addToBackStack(null)  // Add this transaction to the back stack
                    .commit();
        });

        setPlantAdapter();
    }

    private void setPlantAdapter() {
        // Example list of plants
        List<Plant> plantList = new ArrayList<>();
        plantList.add(new Plant("White Rose"));
        plantList.add(new Plant("White Tulip"));
        plantList.add(new Plant("Red Tulip"));
        plantList.add(new Plant("Pink Tulip"));
        plantList.add(new Plant("Red Rose"));
        plantList.add(new Plant("Orchid"));
        plantList.add(new Plant("Dalia"));

        // Set up adapter and RecyclerView
        MyPlantAdapter plantAdapter = new MyPlantAdapter(requireActivity(), plantList);
        gardenDetailBinding.recyclerViewPlants.setAdapter(plantAdapter);
        gardenDetailBinding.recyclerViewPlants.setLayoutManager(new LinearLayoutManager(requireActivity()));
    }
}