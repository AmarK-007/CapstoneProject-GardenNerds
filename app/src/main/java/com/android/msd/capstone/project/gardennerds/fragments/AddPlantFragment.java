package com.android.msd.capstone.project.gardennerds.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentAddPlantBinding;
import com.android.msd.capstone.project.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.gardennerds.models.Plant;
import com.android.msd.capstone.project.gardennerds.models.Reminder;
import com.android.msd.capstone.project.gardennerds.viewmodels.PlantViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPlantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPlantFragment extends Fragment implements View.OnClickListener, AddReminderFragment.OnReminderAddedListener {

    private static final String TAG = AddPlantFragment.class.getSimpleName();
    private FragmentAddPlantBinding addPlantBinding;

    private List<Reminder> reminderlist = new ArrayList<>();
    private PlantViewModel plantViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private int gardenId;

    public AddPlantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AddPlantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPlantFragment newInstance(int param1) {
        AddPlantFragment fragment = new AddPlantFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            gardenId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addPlantBinding = FragmentAddPlantBinding.inflate(inflater, container, false);
        init();
        return addPlantBinding.getRoot();
    }

    private void init() {
        addPlantBinding.fabAddReminder.setOnClickListener(this);
        addPlantBinding.fabSavePlant.setOnClickListener(this);

        // Get the ViewModel
        plantViewModel = new ViewModelProvider(requireActivity()).get(PlantViewModel.class);

        //saving gardenId
        plantViewModel.setGardenId(gardenId);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == addPlantBinding.fabSavePlant.getId()) {
            savePlant();
        } else if (v.getId() == addPlantBinding.fabAddReminder.getId()) {
            // Replace the current fragment with AddPlantFragment
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frames, new AddReminderFragment())
                    .addToBackStack(null)  // Add this transaction to the back stack
                    .commit();
        }
    }

    private void savePlant() {
        if (validateInputs()) {
            Plant plant = new Plant();
            plant.setPlantName(addPlantBinding.edtPlantName.getText().toString());
            plant.setPlantType(getPlantType());
            plant.setMoistureLevel(addPlantBinding.edtPlantMoistureLevel.getText().toString());
            plant.setTemperatureLevel(addPlantBinding.edtPlantTemperature.getText().toString());
            plant.setWateringInterval(addPlantBinding.edtPlantWateringInterval.getText().toString());
            plant.setSunlightLevel(getSunlightPreference());
            plant.setNutrientRequired(addPlantBinding.edtPlantNutritionRequired.getText().toString());
            plant.setGardenId(plantViewModel.getGardenId());

            // Insert into the database
            PlantDataSource plantDataSource = new PlantDataSource(requireContext());
            boolean isInserted = plantDataSource.insertPlant(plant);

            if (isInserted) {
                Toast.makeText(requireContext(), "Plant added successfully!", Toast.LENGTH_SHORT).show();

                // Fetch updated list of plants and update ViewModel
                List<Plant> updatedPlants = plantDataSource.getPlantsByGardenId(plantViewModel.getGardenId());
                plantViewModel.setPlantList(updatedPlants);

                getActivity().getSupportFragmentManager().popBackStack();
            }
        }
    }

    // Java example
    public boolean validateInputs() {
        String plantName = addPlantBinding.edtPlantName.getText().toString().trim();
        String moistureLevel = addPlantBinding.edtPlantMoistureLevel.getText().toString().trim();
        String temperature = addPlantBinding.edtPlantTemperature.getText().toString().trim();
        String wateringInterval = addPlantBinding.edtPlantWateringInterval.getText().toString().trim();
        String nutritionRequired = addPlantBinding.edtPlantNutritionRequired.getText().toString().trim();

        // Validate Plant Name
        if (plantName.isEmpty()) {
            addPlantBinding.edtPlantName.setError("Plant name is required");
            return false;
        }

        // Validate Moisture Level (Decimal number)
        if (moistureLevel.isEmpty()) {
            addPlantBinding.edtPlantMoistureLevel.setError("Moisture level is required");
            return false;
        } else {
            try {
                Double.parseDouble(moistureLevel);
            } catch (NumberFormatException e) {
                addPlantBinding.edtPlantMoistureLevel.setError("Invalid moisture level");
                return false;
            }
        }

        // Validate Temperature (Integer number)
        if (temperature.isEmpty()) {
            addPlantBinding.edtPlantTemperature.setError("Temperature is required");
            return false;
        } else {
            try {
                Integer.parseInt(temperature);
            } catch (NumberFormatException e) {
                addPlantBinding.edtPlantTemperature.setError("Invalid temperature");
                return false;
            }
        }

        // Validate Watering Interval (Time format)
        if (wateringInterval.isEmpty()) {
            addPlantBinding.edtPlantWateringInterval.setError("Watering interval is required");
            return false;
        }

        // Validate Nutrition Requirement
        if (nutritionRequired.isEmpty()) {
            addPlantBinding.edtPlantNutritionRequired.setError("Nutrition required is required");
            return false;
        }

        // All validations passed
        return true;
    }

    @Override
    public void onReminderAdded(Reminder reminder) {
        reminderlist.add(reminder);

    }

    public interface OnPlantAddedListener {
        void onPlantAdded(Plant plant);
    }

    private String getSunlightPreference() {

        if (addPlantBinding.rbFullSunlight.isChecked()) return "Full Sunlight";
        else if (addPlantBinding.rbPartialSunlight.isChecked()) return "PartialSunlight";
        else if (addPlantBinding.rbShady.isChecked()) return "Shady";
        else return "";
    }

    private String getPlantType() {

        if (addPlantBinding.rbIndoor.isChecked()) return "Indoor";
        else return "Outdoor";
    }
}