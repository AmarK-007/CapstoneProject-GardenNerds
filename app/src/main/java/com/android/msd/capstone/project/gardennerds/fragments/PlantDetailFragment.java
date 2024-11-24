package com.android.msd.capstone.project.gardennerds.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.adapters.MyPlantAdapter;
import com.android.msd.capstone.project.gardennerds.adapters.ReminderAdapter;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentGardenDetailBinding;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentPlantDetailBinding;
import com.android.msd.capstone.project.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.gardennerds.db.ReminderDataSource;
import com.android.msd.capstone.project.gardennerds.models.Garden;
import com.android.msd.capstone.project.gardennerds.models.Plant;
import com.android.msd.capstone.project.gardennerds.models.Reminder;
import com.android.msd.capstone.project.gardennerds.viewmodels.ReminderViewModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlantDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlantDetailFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ReminderViewModel reminderViewModel;
    private ReminderAdapter reminderAdapter;

    private FragmentPlantDetailBinding plantDetailBinding;

    public PlantDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlantDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlantDetailFragment newInstance(String param1, String param2) {
        PlantDetailFragment fragment = new PlantDetailFragment();
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
        plantDetailBinding = FragmentPlantDetailBinding.inflate(inflater, container, false);
        init();
        return plantDetailBinding.getRoot();
    }

    private void init(){

        // Initialize ViewModel
         reminderViewModel= new ViewModelProvider(this).get(ReminderViewModel.class);

        // Retrieve the passed garden object
        if (getArguments() != null) {
            Plant plant = (Plant) getArguments().getParcelable("plant");

            //saving garden Id
            //plantViewModel.setGardenId(plant.getPlantId());

            // Set data to the views
            plantDetailBinding.textViewPlantName.setText(plant.getPlantName());
            plantDetailBinding.textViewPlantType.setText("Plant Type: "+plant.getPlantType());
            //plantDetailBinding.textViewSunlightPreference.setText("Sunlight Required: " + plant.getSunlightLevel());
            //plantDetailBinding.textViewWateringFrequency.setText("Watering Frequency: " + plant.getWateringInterval() + "days");
            //plantDetailBinding.textViewMoistureLevel.setText("Garden Area: " + plant.getMoistureLevel());

            // Use image loading library like Glide to load the image
            Glide.with(requireActivity())
                    .load(plant.getImageUri())
                    .placeholder(R.drawable.ic_plant)  // default image
                    .into(plantDetailBinding.imageViewGarden);
        }

        plantDetailBinding.fabAddReminder.setOnClickListener(this);

        setReminderAdapter();
    }

    private void setReminderAdapter() {
        // Set up adapter and RecyclerView
        plantDetailBinding.recyclerViewReminders.setLayoutManager(new LinearLayoutManager(requireActivity()));
        reminderAdapter = new ReminderAdapter(new ArrayList<>(),requireActivity());
        plantDetailBinding.recyclerViewReminders.setAdapter(reminderAdapter);


        // Observe the plant list for updates
        reminderViewModel.getReminderList().observe(getViewLifecycleOwner(), reminders -> {
            if (reminders != null && !reminders.isEmpty()) {
                reminderAdapter.setReminders(reminders); // Refresh the RecyclerView
                plantDetailBinding.tvNoReminders.setVisibility(View.GONE);
            }else{
                plantDetailBinding.tvNoReminders.setVisibility(View.VISIBLE);
            }
        });

        // Load initial plants for the garden (optional)
        loadInitialReminders(reminderViewModel.getPlantId());
    }

    private void loadInitialReminders(int plantId) {
        //Fetch reminders from the database for the given plant ID
        ReminderDataSource reminderDataSource = new ReminderDataSource(requireContext());
        List<Reminder> reminders = reminderDataSource.getRemindersByPlantId(plantId);
        reminderViewModel.setReminderList(reminders);
    }


    @Override
    public void onClick(View v) {
        // Replace the current fragment with AddReminderFragment
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frames, AddReminderFragment.newInstance(reminderViewModel.getPlantId()))
                .addToBackStack(null)  // Add this transaction to the back stack
                .commit();
    }
}