package com.android.msd.capstone.project.wear.gardennerds.models.tempModels;

public class Reminders {

    public String name;
    public String frequency;
    public  String type;

    public Reminders(String name, String frequency, String type) {
        this.name = name;
        this.frequency = frequency;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
