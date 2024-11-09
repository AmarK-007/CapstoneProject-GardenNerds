package com.android.msd.capstone.project.gardennerds.network.service;

import com.android.msd.capstone.project.gardennerds.network.response.SoilDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GetSoilDataService {
    @GET("bio/point")
    Call<SoilDataResponse> getSoilData(
            @Header("Authorization") String apiKey,
            @Query("lat") double lat,
            @Query("lng") double lng,
            @Query("params") String params // Add parameters for soilMoisture, soilTemperature
    );
}
