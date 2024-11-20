package com.android.msd.capstone.project.gardennerds.models;

import java.io.Serializable;

public class Garden implements Serializable {

    private int gardenId = -1;
    private String name;
    private String description;
    private String gardenArea;
    private String gardenLatitude;
    private String gardenLongitude;
    private String sunlightPreference;
    private String wateringFrequency;
    private String imageUri;  // URL or URI to the garden image
    private int userId = -1;

    public Garden(String name, String description, String gardenArea,String gardenLatitude, String gardenLongitude, String sunlightPreference, String wateringFrequency, String imageUri) {

        this.name = name;
        this.description = (description != null && !description.isEmpty()) ? description : "Description";
        this.gardenArea = gardenArea;
        this.gardenLatitude = (gardenLatitude != null && !gardenLatitude.isEmpty()) ? gardenLatitude : "";
        this.gardenLongitude = (gardenLongitude != null && !gardenLongitude.isEmpty()) ? gardenLongitude : "";
        this.sunlightPreference = (sunlightPreference != null && !sunlightPreference.isEmpty()) ? sunlightPreference : "";
        this.wateringFrequency = (wateringFrequency != null && !wateringFrequency.isEmpty()) ? wateringFrequency : "";
        this.imageUri = imageUri;
    }

    public Garden() {
    }

    public int getGardenId() {
        return gardenId;
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
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

    public String getGardenLatitude() {
        return gardenLatitude;
    }

    public void setGardenLatitude(String gardenLatitude) {
        this.gardenLatitude = gardenLatitude;
    }

    public String getGardenLongitude() {
        return gardenLongitude;
    }

    public void setGardenLongitude(String gardenLongitude) {
        this.gardenLongitude = gardenLongitude;
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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
