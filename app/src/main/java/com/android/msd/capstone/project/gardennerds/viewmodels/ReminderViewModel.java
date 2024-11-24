package com.android.msd.capstone.project.gardennerds.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.msd.capstone.project.gardennerds.models.Reminder;

import java.util.List;

public class ReminderViewModel extends ViewModel {
    // MutableLiveData to store the list of reminders
    private final MutableLiveData<List<Reminder>> reminderList = new MutableLiveData<>();
    private int plantId = -1;

    // Method to update the plant list
    public void setReminderList(List<Reminder> reminders) {
        reminderList.setValue(reminders);
    }

    // Method to retrieve the plant list as LiveData
    public LiveData<List<Reminder>> getReminderList() {
        return reminderList;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }
}
