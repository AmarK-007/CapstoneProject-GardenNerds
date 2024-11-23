package com.android.msd.capstone.project.gardennerds.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Garden implements Parcelable {

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

    private ArrayList<Plant> plants;

    public Garden(String name, String description, String gardenArea, String gardenLatitude, String gardenLongitude, String sunlightPreference, String wateringFrequency, String imageUri, int userId, ArrayList<Plant> plants) {

        this.name = name;
        this.description = (description != null && !description.isEmpty()) ? description : "Description";
        this.gardenArea = gardenArea;
        this.gardenLatitude = (gardenLatitude != null && !gardenLatitude.isEmpty()) ? gardenLatitude : "";
        this.gardenLongitude = (gardenLongitude != null && !gardenLongitude.isEmpty()) ? gardenLongitude : "";
        this.sunlightPreference = (sunlightPreference != null && !sunlightPreference.isEmpty()) ? sunlightPreference : "";
        this.wateringFrequency = (wateringFrequency != null && !wateringFrequency.isEmpty()) ? wateringFrequency : "";
        this.imageUri = imageUri;
        this.userId = userId;
        this.plants = plants;

    }

    public Garden() {
        this.plants = new ArrayList<>();
    }

    protected Garden(Parcel in) {
        gardenId = in.readInt();
        name = in.readString();
        description = in.readString();
        gardenArea = in.readString();
        gardenLatitude = in.readString();
        gardenLongitude = in.readString();
        sunlightPreference = in.readString();
        wateringFrequency = in.readString();
        imageUri = in.readString();
        userId = in.readInt();
        plants = in.createTypedArrayList(Plant.CREATOR);
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

    public ArrayList<Plant> getPlants() {
        return plants;
    }

    public void setPlants(ArrayList<Plant> plants) {
        this.plants = plants;
    }

    public void addPlant(Plant plant) {
        this.plants.add(plant);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(gardenId);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(gardenArea);
        dest.writeString(gardenLatitude);
        dest.writeString(gardenLongitude);
        dest.writeString(sunlightPreference);
        dest.writeString(wateringFrequency);
        dest.writeString(imageUri);
        dest.writeInt(userId);
        dest.writeTypedList(plants);
    }

    public static final Creator<Garden> CREATOR = new Creator<Garden>() {
        @Override
        public Garden createFromParcel(Parcel in) {
            return new Garden(in);
        }

        @Override
        public Garden[] newArray(int size) {
            return new Garden[size];
        }
    };

}
