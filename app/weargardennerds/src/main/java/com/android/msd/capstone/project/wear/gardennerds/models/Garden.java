package com.android.msd.capstone.project.wear.gardennerds.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Represents a Garden entity with various attributes and implements Parcelable for easy
 * passing between Android components.
 */
public class Garden implements Parcelable {

    // Unique identifier for the garden
    private int gardenId = -1;
    // Name of the garden
    private String name;
    // Description of the garden
    private String description;
    // Area of the garden
    private String gardenArea;
    // Latitude coordinate of the garden
    private String gardenLatitude;
    // Longitude coordinate of the garden
    private String gardenLongitude;
    // Sunlight preference for the garden
    private String sunlightPreference;
    // Watering frequency for the garden
    private String wateringFrequency;
    // URI to the garden image
    private String imageUri;
    // User ID associated with the garden
    private int userId = -1;

    // List of plants in the garden
    private ArrayList<Plant> plants;

    /**
     * Constructor to initialize a Garden with all attributes.
     *
     * @param name               Name of the garden
     * @param description        Description of the garden
     * @param gardenArea         Area of the garden
     * @param gardenLatitude     Latitude coordinate of the garden
     * @param gardenLongitude    Longitude coordinate of the garden
     * @param sunlightPreference Sunlight preference for the garden
     * @param wateringFrequency  Watering frequency for the garden
     * @param imageUri           URI to the garden image
     * @param userId             User ID associated with the garden
     * @param plants             List of plants in the garden
     */
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

    /**
     * Constructor to initialize a Garden with minimal attributes.
     *
     * @param name        Name of the garden
     * @param description Description of the garden
     * @param gardenArea  Area of the garden
     */
    public Garden(String name, String description, String gardenArea) {
        this.name = name;
        this.description = (description != null && !description.isEmpty()) ? description : "Description";
        this.gardenArea = gardenArea;
        this.plants = new ArrayList<>();
    }

    /**
     * Default constructor to initialize an empty Garden.
     */
    public Garden() {
        this.plants = new ArrayList<>();
    }

    /**
     * Constructor to create a Garden from a Parcel.
     *
     * @param in Parcel containing the Garden data
     */
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

    // Getters and setters for the Garden attributes
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

    /**
     * Write the Garden data to a Parcel
     *
     * @param dest  Parcel to write the Garden data
     * @param flags Additional flags about how the object should be written
     */
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

    /**
     * Creator for Garden
     * Required for Parcelable
     * Creates a Garden from a Parcel
     *
     * @param in Parcel containing the Garden data
     * @return Garden
     */
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