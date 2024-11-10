package com.android.msd.capstone.project.gardennerds.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.msd.capstone.project.gardennerds.models.productResponses.ProductDetail;
import com.android.msd.capstone.project.gardennerds.models.productResponses.ProductResults;
import com.android.msd.capstone.project.gardennerds.retrofitApiCall.ApiService;
import com.android.msd.capstone.project.gardennerds.retrofitApiCall.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private ApiService apiService;
    public static final String apiKey = "525f949b49a6440982fa7faade39d1757d915944f05df7776cc224e50f96b054";

    public ProductRepository() {
        this.apiService =  RetrofitClient.getApiService();
    }

    public LiveData<ProductResults> fetchProductDetails(String productID){
        MutableLiveData<ProductResults> data = new MutableLiveData<>();
        apiService.getProductResults("google_product", productID, "google.com", apiKey).enqueue(new Callback<ProductDetail>() {

            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.isSuccessful() && response.body() != null){
                    data.setValue(response.body().getProductResults());
                }else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }
}
