package com.android.msd.capstone.project.gardennerds.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Reminder> reminder = new MutableLiveData<>();

    public void setReminder(Reminder reminder) {
        this.reminder.setValue(reminder);
    }

    public LiveData<Reminder> getReminder() {
        return reminder;
    }
}
