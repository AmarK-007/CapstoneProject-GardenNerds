package com.android.msd.capstone.project.gardennerds.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.adapters.MyPlantAdapter;
import com.android.msd.capstone.project.gardennerds.adapters.ReminderAdapter;
import com.android.msd.capstone.project.gardennerds.broadcastReceivers.newReminder.ReminderManager;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentGardenDetailBinding;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentPlantDetailBinding;
import com.android.msd.capstone.project.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.gardennerds.db.ReminderDataSource;
import com.android.msd.capstone.project.gardennerds.models.Garden;
import com.android.msd.capstone.project.gardennerds.models.Plant;
import com.android.msd.capstone.project.gardennerds.models.Reminder;
import com.android.msd.capstone.project.gardennerds.models.SharedViewModel;
import com.android.msd.capstone.project.gardennerds.utils.SwipeToDeleteCallback;
import com.android.msd.capstone.project.gardennerds.utils.Utility;
import com.android.msd.capstone.project.gardennerds.viewmodels.ReminderViewModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlantDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlantDetailFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ReminderViewModel reminderViewModel;
    private ReminderAdapter reminderAdapter;
    private SharedViewModel sharedViewModel;
    //private boolean remindersLoaded = false;

    private FragmentPlantDetailBinding plantDetailBinding;
    private Plant plant;

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
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        if (getArguments() != null) {
            plant = getArguments().getParcelable("plant");
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Ensure Activity and ActionBar are available
        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getSupportActionBar().setTitle(getString(R.string.text_plant_details));
        }
        sharedViewModel.getReminder().observe(getViewLifecycleOwner(), reminder -> {
            if (reminder != null) {
                saveReminder(reminder);
            }
        });
    }

    private void init() {

        // Initialize ViewModel
        reminderViewModel = new ViewModelProvider(this).get(ReminderViewModel.class);

        // Retrieve the passed garden object
        if (plant != null) {
            //saving garden Id
            //plantViewModel.setGardenId(plant.getPlantId());

            // Set data to the views

            plantDetailBinding.textViewPlantName.setText(plant.getPlantName() + " - " + plant.getPlantType());
            // plantDetailBinding.textViewPlantType.setText("Plant Type: " + plant.getPlantType());
            plantDetailBinding.textViewSunlightPreference.setText("Sunlight Required: " + plant.getSunlightLevel());
            plantDetailBinding.textViewWateringFrequency.setText("Watering Frequency: " + plant.getWateringInterval() + "days");
            plantDetailBinding.textViewMoistureLevel.setText("Garden Area: " + plant.getMoistureLevel());
            plantDetailBinding.textViewNutrientLevel.setText("Nutrient Level: " + plant.getNutrientRequired());
            reminderViewModel.setPlantId(plant.getPlantId());


            // Use image loading library like Glide to load the image
            Glide.with(requireActivity())
                    .load(plant.getImageUri())
                    .placeholder(R.drawable.ic_plant)  // default image
                    .into(plantDetailBinding.imageViewGarden);

        }

        plantDetailBinding.fabAddReminder.setOnClickListener(this);

        setReminderAdapter();

        // Load initial reminders after the adapter is set
        if (plant != null) {
            loadInitialReminders(plant.getPlantId());
        }
    }

    private void setReminderAdapter() {
        // Set up adapter and RecyclerView
        plantDetailBinding.recyclerViewReminders.setLayoutManager(new LinearLayoutManager(requireActivity()));
        reminderAdapter = new ReminderAdapter(new ArrayList<>(), requireActivity());
        plantDetailBinding.recyclerViewReminders.setAdapter(reminderAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(position -> {
            Reminder reminderToDelete = reminderAdapter.getReminderAt(position);

            // Delete garden via ViewModel
            deleteReminder(reminderToDelete);

            // Notify adapter of item removal
            loadInitialReminders(plant.getPlantId());
        }));

        itemTouchHelper.attachToRecyclerView(plantDetailBinding.recyclerViewReminders);


        // Observe the plant list for updates
        reminderViewModel.getReminderList().observe(getViewLifecycleOwner(), reminders -> {
            if (reminders != null && !reminders.isEmpty()) {
                reminderAdapter.setReminders(reminders);

                plantDetailBinding.tvNoReminders.setVisibility(View.GONE);
            } else {
                plantDetailBinding.tvNoReminders.setVisibility(View.VISIBLE);
            }
        });

//        // Observe the shared ViewModel for new reminders
//        sharedViewModel.getReminder().observe(getViewLifecycleOwner(), reminder -> {
//            if (reminder != null) {
//                saveReminder(reminder);
//            }
//        });

//        if (!remindersLoaded) {
//            // Load initial plants for the garden (optional)
//            loadInitialReminders(reminderViewModel.getPlantId());
//            remindersLoaded = true;
//        }

    }

    private void loadInitialReminders(int plantId) {
        //Fetch reminders from the database for the given plant ID
        ReminderDataSource reminderDataSource = new ReminderDataSource(requireContext());
        List<Reminder> reminders = reminderDataSource.getRemindersByPlantId(plantId);
        // Clear the adapter before adding new items
        reminderAdapter.setReminders(new ArrayList<>());
        reminderAdapter.notifyDataSetChanged();

        // Add new items to the adapter
        reminderAdapter.setReminders(reminders);
        reminderAdapter.notifyDataSetChanged();

        // Update the ViewModel
        reminderViewModel.setReminderList(reminders);
    }


    @Override
    public void onClick(View v) {
        // Replace the current fragment with AddReminderFragment
//        Bundle bundle = new Bundle();
//        bundle.putInt("plantID", plant.getPlantId());
//
//        AddReminderFragment fragment = new AddReminderFragment();
//        fragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frames, AddReminderFragment.newInstance(reminderViewModel.getPlantId()))
                .addToBackStack(null)  // Add this transaction to the back stack
                .commit();
    }

    public void saveReminder(Reminder reminder) {
        ReminderDataSource reminderDataSource = new ReminderDataSource(requireContext());
        reminder.setPlantId(plant.getPlantId());

        // Check if the reminder already exists
        List<Reminder> existingReminders = reminderDataSource.getRemindersByPlantId(plant.getPlantId());
        for (Reminder existingReminder : existingReminders) {
            if (existingReminder.getReminderTypeId() == reminder.getReminderTypeId()) {
                // Reminder already exists, do not insert again
                 Toast.makeText(requireContext(), Utility.getReminderTypeString(requireContext(), reminder.getReminderTypeId()) + " Reminder already exists", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Log.d("ReminderID", reminder.getReminderId() + " Actual reminder ID");
        long isInserted = reminderDataSource.insertReminder(reminder);
        if (isInserted > 0) {
            List<Reminder> updatedReminders = reminderDataSource.getRemindersByPlantId(plant.getPlantId());
            reminderViewModel.setReminderList(updatedReminders);
            // Update the adapter's list and notify it
            reminderAdapter.setReminders(updatedReminders);
            Utility.setSnoozeReminder(requireContext(), false, reminder);
            /**Above method is working amar  below method has bugs*/
//            ReminderManager reminderManager = new ReminderManager(requireContext());
//            reminderManager.startReminder(reminder.getReminderId());
            /**it is till here, its new one that requires all things for reminder*/
            // Optionally, hide "No Reminders" message if any reminders exist
            if (!updatedReminders.isEmpty()) {
                plantDetailBinding.tvNoReminders.setVisibility(View.GONE);
            }
        }
    }


    private void deleteReminder(Reminder reminder) {
        ReminderDataSource reminderDataSource = new ReminderDataSource(requireActivity());
        Utility.cancelReminder(requireContext(), reminder.getReminderTypeId(), reminder.getPlantId());
        // Delete the reminder itself
        reminderDataSource.deleteReminder(reminder);
    }
}