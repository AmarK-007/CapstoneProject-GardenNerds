package com.android.msd.capstone.project.gardennerds.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.msd.capstone.project.gardennerds.models.shoppingResponses.ShoppingResult;
import com.android.msd.capstone.project.gardennerds.repositories.ShoppingRepository;

import java.util.List;

public class ShoppingViewModel extends ViewModel {
    private final ShoppingRepository repository;

    public ShoppingViewModel() {
        repository = new ShoppingRepository();
    }

    public LiveData<List<ShoppingResult>> getShoppingResults(String query) {
        return repository.fetchShoppingResults(query);
    }
}
