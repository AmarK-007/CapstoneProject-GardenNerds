package com.android.msd.capstone.project.gardennerds.fragments;

import static com.android.msd.capstone.project.gardennerds.utils.Constants.API_KEY;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.msd.capstone.project.gardennerds.R;
import com.android.msd.capstone.project.gardennerds.databinding.FragmentSoilSensorBinding;
import com.android.msd.capstone.project.gardennerds.network.RetrofitClient;
import com.android.msd.capstone.project.gardennerds.network.response.SoilDataResponse;
import com.android.msd.capstone.project.gardennerds.network.service.GetSoilDataService;
import com.android.msd.capstone.project.gardennerds.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
 * Use the {@link SoilSensorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoilSensorFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FragmentSoilSensorBinding binding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SoilSensorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SoilSensorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SoilSensorFragment newInstance(String param1, String param2) {
        SoilSensorFragment fragment = new SoilSensorFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentSoilSensorBinding.inflate(inflater, container, false);
        binding.buttonGetSoilData.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.buttonGetSoilData.getId()) {
            //fetchSoilData();
            fetchFromDummyData();
        }
    }

    private void fetchSoilData() {
        GetSoilDataService apiService = RetrofitClient.getRetrofitInstance().create(GetSoilDataService.class);
        double lat = 50.714691; // Default latitude
        double lng = 4.399100;  // Default longitude
        String params = "soilMoisture,soilTemperature";

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
                        binding.textviewResult.setText(result);
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
            binding.textviewResult.setText(result);
        } else {
            // Handle the case where hours list is empty
            binding.textviewResult.setText("No data available.");
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
}