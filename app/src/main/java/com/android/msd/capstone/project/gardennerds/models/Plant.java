package com.android.msd.capstone.project.gardennerds.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

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
    private String moistureLevel;
    private String wateringInterval;
    private String temperatureLevel;
    private String sunlightLevel;
    private String nutrientRequired;

    private ArrayList<Reminder> reminders;

    /**
     * Constructor for Plant
     */
    public Plant() {
        this.reminders = new ArrayList<>();
    }


    // Getters and Setters
    public Plant(String plantName){
        this.plantName = plantName;
    }

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

    public String getMoistureLevel() {
        return moistureLevel;
    }

    public void setMoistureLevel(String moistureLevel) {
        this.moistureLevel = moistureLevel;
    }

    public String getWateringInterval() {
        return wateringInterval;
    }

    public void setWateringInterval(String wateringInterval) {
        this.wateringInterval = wateringInterval;
    }

    public String getTemperatureLevel() {
        return temperatureLevel;
    }

    public void setTemperatureLevel(String temperatureLevel) {
        this.temperatureLevel = temperatureLevel;
    }

    public String getSunlightLevel() {
        return sunlightLevel;
    }

    public void setSunlightLevel(String sunlightLevel) {
        this.sunlightLevel = sunlightLevel;
    }

    public String getNutrientRequired() {
        return nutrientRequired;
    }

    public void setNutrientRequired(String nutrientRequired) {
        this.nutrientRequired = nutrientRequired;
    }

    public ArrayList<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(ArrayList<Reminder> reminders) {
        this.reminders = reminders;
    }

    public void addReminder(Reminder reminder) {
        reminders.add(reminder);
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
        moistureLevel = in.readString();
        wateringInterval = in.readString();
        temperatureLevel = in.readString();
        sunlightLevel = in.readString();
        nutrientRequired = in.readString();
        reminders = in.createTypedArrayList(Reminder.CREATOR);
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
        parcel.writeString(moistureLevel);
        parcel.writeString(wateringInterval);
        parcel.writeString(temperatureLevel);
        parcel.writeString(sunlightLevel);
        parcel.writeString(nutrientRequired);
        parcel.writeTypedList(reminders);
    }
}