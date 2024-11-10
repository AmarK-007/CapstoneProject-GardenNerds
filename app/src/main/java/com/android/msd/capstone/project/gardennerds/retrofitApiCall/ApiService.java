package com.android.msd.capstone.project.gardennerds.retrofitApiCall;

import com.android.msd.capstone.project.gardennerds.models.productResponses.ProductDetail;
import com.android.msd.capstone.project.gardennerds.models.shoppingResponses.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("search.json")
    Call<SearchResponse> getShoppingResults(
            @Query("engine") String engine,
            @Query("q") String query,
            @Query("google_domain") String googleDomain,
//            @Query("device") String device,
            @Query("api_key") String apiKey
    );

    @GET("search.json")
    Call<ProductDetail> getProductResults(
            @Query("engine") String engine,
            @Query("product_id") String productId,
            @Query("google_domain") String googleDomain,
            @Query("api_key") String apiKey
    );
}
