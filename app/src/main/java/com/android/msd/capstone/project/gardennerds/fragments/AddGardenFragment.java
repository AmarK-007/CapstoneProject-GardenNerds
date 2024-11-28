package com.android.msd.capstone.project.gardennerds.fragments;

import static com.android.msd.capstone.project.gardennerds.utils.Constants.API_KEY;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentAddGardenBinding;
import com.android.msd.capstone.project.gardennerds.db.GardenDataSource;
import com.android.msd.capstone.project.gardennerds.models.Garden;
import com.android.msd.capstone.project.gardennerds.network.RetrofitClient;
import com.android.msd.capstone.project.gardennerds.network.response.SoilDataResponse;
import com.android.msd.capstone.project.gardennerds.network.service.ApiService;
import com.android.msd.capstone.project.gardennerds.utils.Constants;
import com.android.msd.capstone.project.gardennerds.viewmodels.GardenViewModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddGardenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddGardenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentAddGardenBinding addGardenBinding;
    private boolean allPermissionsGranted = false;

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private Uri selectedImageUri; // To store the image URI
    private static final String BASE_URL = "https://api.stormglass.io/v2/";
    private FusedLocationProviderClient fusedLocationClient;
    private double latitude, longitude;
    private GardenDataSource gardenDataSource;
    private GardenViewModel gardenViewModel;

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
                        Glide.with(AddGardenFragment.this)
                                .load(selectedImageUri)
                                .into(addGardenBinding.ivGardenPhoto); // Your ImageView in the layout
                    }
                }
            });

    public AddGardenFragment() {
        // Required empty public constructor
    }

    public static AddGardenFragment newInstance(String param1, String param2) {
        AddGardenFragment fragment = new AddGardenFragment();
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Ensure Activity and ActionBar are available
        if (getActivity() != null && getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getSupportActionBar().setTitle(getString(R.string.text_add_garden));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addGardenBinding = FragmentAddGardenBinding.inflate(inflater, container, false);

        init();

        return addGardenBinding.getRoot();
    }

    private void init() {
        // Configure Toolbar
        //setupToolbar();

        //Initialize Garden datasource
        gardenDataSource = new GardenDataSource(requireActivity());

        // Get the ViewModel
        gardenViewModel = new ViewModelProvider(requireActivity()).get(GardenViewModel.class);

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // Request Location Permissions if not already granted
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_LOCATION_PERMISSION);
        }

        // Set listeners
        addGardenBinding.btnUploadPhoto.setOnClickListener(v -> {
            showImageSourceDialog();
        });

        addGardenBinding.btnGetSoilSensorData.setOnClickListener(v -> {
            // Get current location and retrieve soil data from API
            getLocationAndFetchData();
        });

        addGardenBinding.btnSaveGarden.setOnClickListener(v -> {
            // Save garden data (e.g., save to a database or backend)
            saveGardenData();
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

    // Get current location and fetch temperature and moisture data from API
    @SuppressLint("MissingPermission")
    private void getLocationAndFetchData() {
        fusedLocationClient.getLastLocation()
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            Location location = task.getResult();
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                                // Call your API to fetch temperature and moisture data using latitude, longitude
                                //fetchSoilData(latitude, longitude);
                                fetchFromDummyData();
                            }
                        } else {
                            Toast.makeText(requireContext(), "Failed to get location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void fetchSoilData(double lat, double lng) {
        ApiService apiService = RetrofitClient.getRetrofitInstance(BASE_URL).create(ApiService.class);
        //double lat = 50.714691; // Default latitude
        //double lng = 4.399100;  // Default longitude
        String params = "soilMoisture,soilTemperature,surfaceTemperature";

        Call<SoilDataResponse> call = apiService.getSoilData(API_KEY, lat, lng, params);
        call.enqueue(new Callback<SoilDataResponse>() {

            @Override
            public void onResponse(Call<SoilDataResponse> call, Response<SoilDataResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SoilDataResponse.HourData recentData = getCurrentDateData(response.body().getHours());
                    if (recentData != null) {
                        String result = "Time: " + formatDate(recentData.getTime()) + "\n" +
                                "Soil Moisture: " + recentData.getSoilMoisture().getNoaa() + " m³/m³\n" +
                                "Soil Temperature: " + recentData.getSoilTemperature().getNoaa() + " °C\n";
                        addGardenBinding.tvMoisture.setText("Soil Moisture: " + recentData.getSoilMoisture().getNoaa() + " m³/m³");
                        addGardenBinding.tvTemperature.setText("Soil Temperature: " + recentData.getSoilTemperature().getNoaa() + " °C");
                        addGardenBinding.tvSurfaceTemperature.setText("Surface Temperature: " + recentData.getSurfaceTemperature().getNoaa() + " °C");
                    }
                } else {
                    Log.e("API_ERROR", "Error fetching data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<SoilDataResponse> call, Throwable t) {
                Log.e("API_ERROR", "Failure: " + t.getMessage());
            }
        });
    }

    private void fetchFromDummyData() {
        Gson gson = new GsonBuilder().create();
        SoilDataResponse response = gson.fromJson(Constants.dummyJsonResponse, SoilDataResponse.class);

        // Check if hours list is not empty
        if (response.getHours() != null && !response.getHours().isEmpty()) {
            // Get the first HourData
            SoilDataResponse.HourData currentData = response.getHours().get(4);

            // Format the result
            String result = "Time: " + formatDate(currentData.getTime()) + "\n" +
                    "Soil Moisture: " + currentData.getSoilMoisture().getNoaa() + " m³/m³\n" +
                    "Soil Temperature: " + currentData.getSoilTemperature().getNoaa() + " °C\n";

            // Set the result to the TextView
            addGardenBinding.tvMoisture.setText("Soil Moisture: " + currentData.getSoilMoisture().getNoaa() + " m³/m³");
            addGardenBinding.tvTemperature.setText("Soil Temperature: " + currentData.getSoilTemperature().getNoaa() + " °C");
            addGardenBinding.tvSurfaceTemperature.setText("Surface Temperature: " + currentData.getSurfaceTemperature().getNoaa() + " °C");
        } else {
            // Handle the case where hours list is empty
            addGardenBinding.tvMoisture.setText("No data available.");
        }
    }

    private SoilDataResponse.HourData getCurrentDateData(List<SoilDataResponse.HourData> hours) {
        SoilDataResponse.HourData currentData = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault());
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        for (SoilDataResponse.HourData hourData : hours) {
            try {
                Date date = dateFormat.parse(hourData.getTime());
                String formattedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);

                if (formattedDate.equals(currentDate)) {
                    currentData = hourData;
                    break; // Get the first occurrence of today's data
                }
            } catch (ParseException e) {
                Log.e("DATE_ERROR", "Date parsing error: " + e.getMessage());
            }
        }
        return currentData;
    }

    private String formatDate(String time) {
        String formattedDate = "";
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy 'at' hh:mm a", Locale.getDefault());
        try {
            Date date = inputFormat.parse(time);
            formattedDate = outputFormat.format(date);
        } catch (ParseException e) {
            Log.e("DATE_ERROR", "Date parsing error: " + e.getMessage());
        }
        return formattedDate;
    }

    // Save garden data to the database
    private void saveGardenData() {
        // Validate input fields
        String name = addGardenBinding.etGardenName.getText().toString().trim();
        String description = addGardenBinding.etGardenDescription.getText().toString().trim();
        String areaMeasurement = addGardenBinding.etGardenArea.getText().toString().trim();
        String sunlightPreference = getSunlightPreference();
        String wateringFrequency = addGardenBinding.etWateringInterval.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || areaMeasurement.isEmpty() || sunlightPreference.isEmpty() || wateringFrequency.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        } else if (Integer.parseInt(wateringFrequency) < 1 || Integer.parseInt(wateringFrequency) > 30) {
            Toast.makeText(requireContext(), "Please enter a valid watering interval (1 - 30 days)!", Toast.LENGTH_SHORT).show();
            addGardenBinding.etWateringInterval.setError("Enter a valid watering interval (1 - 30 days)");
            return;
        } else if (Integer.parseInt(areaMeasurement) < 1 || Integer.parseInt(areaMeasurement) > 100) {
            Toast.makeText(requireContext(), "Please enter a valid garden area (1 - 100 sq. ft.)!", Toast.LENGTH_SHORT).show();
            addGardenBinding.etGardenArea.setError("Enter a valid garden area (1 - 100 sq. ft.)");
            return;
        }

        // Get latitude and longitude (already fetched or default to 0.0 if not available)
        double latitude = this.latitude != 0.0 ? this.latitude : 0.0;
        double longitude = this.longitude != 0.0 ? this.longitude : 0.0;

        // Get image URI (placeholder in this case)
        //String imageUri = "example_image_uri"; // Replace with actual image URI after uploading photo

        // Create Garden object
        Garden garden = new Garden();
        garden.setName(name);
        garden.setDescription(description);
        garden.setGardenArea(areaMeasurement);
        garden.setSunlightPreference(sunlightPreference);
        garden.setWateringFrequency(wateringFrequency);
        garden.setGardenLatitude(String.valueOf(latitude));
        garden.setGardenLongitude(String.valueOf(longitude));
        garden.setImageUri(getImagePath());
        garden.setUserId(1); // Default user ID; replace as needed

        // Insert into database
        boolean isInserted = gardenDataSource.insertGarden(garden);

        if (isInserted) {
            Toast.makeText(requireContext(), "Garden saved successfully!", Toast.LENGTH_SHORT).show();

            // Fetch updated list and update ViewModel
            List<Garden> updatedGardens = gardenDataSource.getAllGardens();
            gardenViewModel.setGardenList(updatedGardens);

            // Navigate back to the previous fragment
            requireActivity().getSupportFragmentManager().popBackStack();
        } else {
            Toast.makeText(requireContext(), "Failed to save garden!", Toast.LENGTH_SHORT).show();
        }
    }

    // Clear input form
    private void clearForm() {
        addGardenBinding.etGardenName.setText("");
        addGardenBinding.etGardenDescription.setText("");
        addGardenBinding.etGardenArea.setText("");
        addGardenBinding.etWateringInterval.setText("");
    }

    private String getSunlightPreference() {

        if (addGardenBinding.rbFullSunlight.isChecked()) return "Full Sunlight";
        else if (addGardenBinding.rbPartialSunlight.isChecked()) return "PartialSunlight";
        else if (addGardenBinding.rbShady.isChecked()) return "Shady";
        else return "";
    }
}