package com.android.msd.capstone.project.gardennerds.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.adapters.MyPlantAdapter;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentGardenDetailBinding;
import com.android.msd.capstone.project.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.gardennerds.models.Garden;
import com.android.msd.capstone.project.gardennerds.models.Plant;
import com.android.msd.capstone.project.gardennerds.viewmodels.PlantViewModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GardenDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GardenDetailFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentGardenDetailBinding gardenDetailBinding;
    private MyPlantAdapter plantAdapter;

    private PlantViewModel plantViewModel;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Ensure Activity and ActionBar are available
        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getSupportActionBar().setTitle(getString(R.string.text_garden_Details));
        }
    }

    private void init() {

        // Get the ViewModel
        plantViewModel = new ViewModelProvider(requireActivity()).get(PlantViewModel.class);

        // Retrieve the passed garden object
        if (getArguments() != null) {
            Garden garden = (Garden) getArguments().getParcelable("garden");

            //saving garden Id
            plantViewModel.setGardenId(garden.getGardenId());

            // Set data to the views
            gardenDetailBinding.textViewGardenName.setText(garden.getName());
            gardenDetailBinding.textViewDescription.setText(garden.getDescription());
            gardenDetailBinding.textViewSunlightPreference.setText("Sunlight Required: " + garden.getSunlightPreference());
            gardenDetailBinding.textViewWateringFrequency.setText("Watering Frequency: " + garden.getWateringFrequency() + "days");
            gardenDetailBinding.textViewMoistureLevel.setText("Garden Area: " + garden.getGardenArea());

            // Use image loading library like Glide to load the image
            Glide.with(requireActivity())
                    .load(garden.getImageUri())  // replace with image URL if available
                    .placeholder(R.drawable.new_garden_nerds)  // default image
                    .into(gardenDetailBinding.imageViewGarden);
        }

        gardenDetailBinding.fabAddPlant.setOnClickListener(this);

        setPlantAdapter();
    }

    private void setPlantAdapter() {

        // Set up adapter and RecyclerView
        gardenDetailBinding.recyclerViewPlants.setLayoutManager(new LinearLayoutManager(requireActivity()));
        plantAdapter = new MyPlantAdapter(new ArrayList<>(),requireActivity());
        gardenDetailBinding.recyclerViewPlants.setAdapter(plantAdapter);


        // Observe the plant list for updates
        plantViewModel.getPlantList().observe(getViewLifecycleOwner(), plants -> {
            if (plants != null && !plants.isEmpty()) {
                plantAdapter.updateDataset(plants); // Refresh the RecyclerView
                gardenDetailBinding.tvNoPlants.setVisibility(View.GONE);
            }else{
                gardenDetailBinding.tvNoPlants.setVisibility(View.VISIBLE);
            }
        });

        // Load initial plants for the garden (optional)
        loadInitialPlants(plantViewModel.getGardenId());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == gardenDetailBinding.fabAddPlant.getId()) {

            // Replace the current fragment with AddPlantFragment
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frames, AddPlantFragment.newInstance(plantViewModel.getGardenId()))
                    .addToBackStack(null)  // Add this transaction to the back stack
                    .commit();
        }
    }

    private void loadInitialPlants(int gardenId) {
        //Fetch plants from the database for the given garden ID
        PlantDataSource plantDataSource = new PlantDataSource(requireContext());
        List<Plant> plants = plantDataSource.getPlantsByGardenId(gardenId);
        plantViewModel.setPlantList(plants);
    }
}