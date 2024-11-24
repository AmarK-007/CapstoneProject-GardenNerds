package com.android.msd.capstone.project.gardennerds.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentAddPlantBinding;
import com.android.msd.capstone.project.gardennerds.db.PlantDataSource;
import com.android.msd.capstone.project.gardennerds.models.Plant;
import com.android.msd.capstone.project.gardennerds.models.Reminder;
import com.android.msd.capstone.project.gardennerds.viewmodels.PlantViewModel;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
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
    private Uri selectedImageUri; // To store the image URI

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private int gardenId;

    // Launcher for the gallery or camera result
    private final ActivityResultLauncher<Intent> resultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        // Handle the result based on the request code (camera or gallery)
                        if (data.getData() != null) {
                            selectedImageUri = data.getData();
                        } else if (data.getExtras() != null) {
                            // Camera case
                            Bundle extras = data.getExtras();
                            if (extras != null) {
                                Bitmap imageBitmap = (Bitmap) extras.get("data");
                                selectedImageUri = getImageUri(requireActivity(), imageBitmap);
                            }
                        }
                        // Display the image in ImageView using Glide
                        Glide.with(requireActivity())
                                .load(selectedImageUri)
                                .into(addPlantBinding.ivGardenPhoto); // Your ImageView in the layout
                    }
                }
            });

    public AddPlantFragment() {
        // Required empty public constructor
    }

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

        // Set listeners
        addPlantBinding.btnUploadPhoto.setOnClickListener(v -> {
            showImageSourceDialog();
        });
    }

    private void showImageSourceDialog() {
        // Show a dialog to select between gallery or camera
        new AlertDialog.Builder(requireActivity())
                .setTitle("Choose Image Source")
                .setItems(new String[]{"Camera", "Gallery"}, (dialog, which) -> {
                    if (which == 0) {
                        // Open Camera
                        openCamera();
                    } else {
                        // Open Gallery
                        openGallery();
                    }
                })
                .show();
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            resultLauncher.launch(takePictureIntent);
        }
    }

    private void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(pickPhoto);
    }

    // Convert Bitmap to Uri (for camera image)
    public Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    // Store image path in DB
    private String getImagePath() {
        if (selectedImageUri != null) {
            return selectedImageUri.toString();
        } else return "";
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
            plant.setImageUri(getImagePath());
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