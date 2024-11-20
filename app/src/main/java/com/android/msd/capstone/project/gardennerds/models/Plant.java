package com.android.msd.capstone.project.gardennerds.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class for Plant
 */
public class Plant implements Parcelable {

    // Plant fields...
    private int plantId;
    private int gardenId;
    private String plantName;
    private String plantType;
    private String growthConditions;

    public Plant() {
    }

    /**
     * Constructor for Plant
     */
    public Plant(int plantId, int gardenId, String plantName, String plantType, String growthConditions) {
        this.plantId = plantId;
        this.gardenId = gardenId;
        this.plantName = plantName;
        this.plantType = plantType;
        this.growthConditions = growthConditions;
    }

    // Getters and Setters
    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public int getGardenId() {
        return gardenId;
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

    public String getGrowthConditions() {
        return growthConditions;
    }

    public void setGrowthConditions(String growthConditions) {
        this.growthConditions = growthConditions;
    }

    /**
     * Constructor for Plant
     */
    protected Plant(Parcel in) {
        plantId = in.readInt();
        gardenId = in.readInt();
        plantName = in.readString();
        plantType = in.readString();
        growthConditions = in.readString();
    }

    /**
     * Creator for Plant
     */
    public static final Creator<Plant> CREATOR = new Creator<Plant>() {
        @Override
        public Plant createFromParcel(Parcel in) {
            return new Plant(in);
        }

        @Override
        public Plant[] newArray(int size) {
            return new Plant[size];
        }
    };

    /**
     * describeContents method
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * writeToParcel method
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(plantId);
        parcel.writeInt(gardenId);
        parcel.writeString(plantName);
        parcel.writeString(plantType);
        parcel.writeString(growthConditions);
    }
}