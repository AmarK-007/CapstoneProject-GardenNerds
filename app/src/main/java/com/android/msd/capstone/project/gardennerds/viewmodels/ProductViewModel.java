package com.android.msd.capstone.project.gardennerds.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.msd.capstone.project.gardennerds.models.productResponses.ProductResults;
import com.android.msd.capstone.project.gardennerds.repositories.ProductRepository;

public class ProductViewModel extends ViewModel {

    private final ProductRepository repository;

    public ProductViewModel() {
        this.repository = new ProductRepository();
    }

    public LiveData<ProductResults> getProductResults(String productId){
        return repository.fetchProductDetails(productId);
    }
}
