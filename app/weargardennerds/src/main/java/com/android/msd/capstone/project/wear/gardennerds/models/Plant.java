package com.android.msd.capstone.project.wear.gardennerds.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Model class for Plant
 */
public class Plant implements Parcelable {

    // Unique identifier for the plant
    private int plantId;
    // Identifier for the garden to which the plant belongs
    private int gardenId;
    // Name of the plant
    private String plantName;
    // Type of the plant
    private String plantType;
    // Growth conditions required for the plant
    private String growthConditions;
    // Moisture level required for the plant
    private String moistureLevel;
    // Watering interval for the plant
    private String wateringInterval;
    // Temperature level required for the plant
    private String temperatureLevel;
    // Sunlight level required for the plant
    private String sunlightLevel;
    // Nutrients required for the plant
    private String nutrientRequired;
    // URI to the plant image
    private String imageUri;

    // List of reminders associated with the plant
    private ArrayList<Reminder> reminders;

    /**
     * Default constructor for Plant
     */
    public Plant() {
        this.reminders = new ArrayList<>();
    }

    /**
     * Constructor for Plant with specific attributes
     *
     * @param plantName        Name of the plant
     * @param sunlightLevel    Sunlight level required for the plant
     * @param wateringInterval Watering interval for the plant
     */
    public Plant(String plantName, String sunlightLevel, String wateringInterval) {
        this.plantName = plantName;
        this.sunlightLevel = sunlightLevel;
        this.wateringInterval = wateringInterval;
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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
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
     * Constructor for Plant from Parcel
     *
     * @param in Parcel containing the Plant data
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
        imageUri = in.readString();
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
     * Describe the contents of the parcel
     *
     * @return int
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write the Plant data to the parcel
     *
     * @param parcel Parcel to write the data to
     * @param i      Additional flags
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
        parcel.writeString(imageUri);
        parcel.writeTypedList(reminders);
    }
}