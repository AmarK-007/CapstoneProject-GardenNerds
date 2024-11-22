package com.android.msd.capstone.project.gardennerds.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.msd.capstone.project.gardennerds.models.shoppingResponses.SearchResponse;
import com.android.msd.capstone.project.gardennerds.models.shoppingResponses.ShoppingResult;
import com.android.msd.capstone.project.gardennerds.retrofitApiCall.ApiService;
import com.android.msd.capstone.project.gardennerds.retrofitApiCall.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingRepository {
    private ApiService apiService;
    public static final String apiKey = "525f949b49a6440982fa7faade39d1757d915944f05df7776cc224e50f96b054";

    public ShoppingRepository() {
        apiService = RetrofitClient.getApiService();
    }

    public LiveData<List<ShoppingResult>> fetchShoppingResults(String query) {
        MutableLiveData<List<ShoppingResult>> data = new MutableLiveData<>();

        apiService.getShoppingResults("google_shopping", query, "google.com" , apiKey).enqueue(new Callback<SearchResponse>() {//"mobile"
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getShoppingResults());
                    Log.e("TAG",""+query+""+apiKey);
                } else {
                    data.setValue(null);
                    Log.e("TAG",""+query+""+apiKey);
                }
            }
            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                data.setValue(null);
                Log.e("TAG",""+query+""+apiKey);
            }
        });

        return data;
    }


}
