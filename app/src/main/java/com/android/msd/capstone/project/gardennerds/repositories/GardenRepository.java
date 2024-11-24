package com.android.msd.capstone.project.gardennerds.repositories;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.msd.capstone.project.gardennerds.models.Weather;
import com.android.msd.capstone.project.gardennerds.network.RetrofitClient;
import com.android.msd.capstone.project.gardennerds.network.response.WeatherResponse;
import com.android.msd.capstone.project.gardennerds.network.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GardenRepository {

    private static final String API_KEY = "ec06757a611eff2ae42b6422c19d898b";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private ApiService apiService;

    public GardenRepository() {
        apiService = RetrofitClient.getRetrofitInstance(BASE_URL).create(ApiService.class);
    }

    public LiveData<Weather> fetchWeatherData(double latitude, double longitude) {
        MutableLiveData<Weather> data = new MutableLiveData<>();
        String apiKey = "25d17a1959c51060fd6f6737087b76e2"; // Replace with your actual API key

        apiService.getWeatherData(latitude, longitude, apiKey).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    Weather weatherData = new Weather(
                            weatherResponse.getMain().getTemp(),
                            weatherResponse.getWeather().get(0).getIcon()
                    );
                    data.setValue(weatherData);
                } else {
                    data.setValue(null); // Handle error state gracefully
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                data.setValue(null); // Handle failure state
            }
        });

        return data;
    }


}
