package com.android.msd.capstone.project.gardennerds.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.msd.capstone.project.gardennerds.models.Garden;

import java.util.List;

public class GardenViewModel extends ViewModel {

    // MutableLiveData to store the list of gardens
    private final MutableLiveData<List<Garden>> gardenList = new MutableLiveData<>();

    // Method to update the garden list
    public void setGardenList(List<Garden> gardens) {
        gardenList.setValue(gardens); // Updates the value and notifies observers
    }

    // Method to retrieve the garden list as LiveData
    public LiveData<List<Garden>> getGardenList() {
        return gardenList; // Observers can watch for updates
    }
}
