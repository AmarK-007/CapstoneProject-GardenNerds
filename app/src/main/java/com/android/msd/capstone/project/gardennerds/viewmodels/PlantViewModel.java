package com.android.msd.capstone.project.gardennerds.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.msd.capstone.project.gardennerds.models.Plant;

import java.util.List;

public class PlantViewModel extends ViewModel {
    // MutableLiveData to store the list of plants
    private final MutableLiveData<List<Plant>> plantList = new MutableLiveData<>();

    // Method to update the plant list
    public void setPlantList(List<Plant> plants) {
        plantList.setValue(plants);
    }

    // Method to retrieve the plant list as LiveData
    public LiveData<List<Plant>> getPlantList() {
        return plantList;
    }
}
