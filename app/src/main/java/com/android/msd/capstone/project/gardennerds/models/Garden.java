package com.android.msd.capstone.project.gardennerds.models;

import java.io.Serializable;

public class Garden implements Serializable {
    private String name;
    private String description;
    private String gardenArea;
    private String sunlightPreference;
    private String wateringFrequency;
    private String moistureLevel;
    private String imageUri;  // URL or URI to the garden image

    public Garden(String name, String description, String gardenArea, String sunlightPreference, String wateringFrequency, String moistureLevel, String imageUri) {

        this.name = name;
        this.description = description;
        this.gardenArea = gardenArea;
        this.sunlightPreference = sunlightPreference;
        this.wateringFrequency = wateringFrequency;
        this.moistureLevel = moistureLevel;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGardenArea() {
        return gardenArea;
    }

    public void setGardenArea(String gardenArea) {
        this.gardenArea = gardenArea;
    }

    public String getSunlightPreference() {
        return sunlightPreference;
    }

    public void setSunlightPreference(String sunlightPreference) {
        this.sunlightPreference = sunlightPreference;
    }

    public String getWateringFrequency() {
        return wateringFrequency;
    }

    public void setWateringFrequency(String wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    public String getMoistureLevel() {
        return moistureLevel;
    }

    public void setMoistureLevel(String moistureLevel) {
        this.moistureLevel = moistureLevel;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
