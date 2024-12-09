package com.android.msd.capstone.project.wear.gardennerds.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class for SoilSensor
 */
public class SoilSensor implements Parcelable {

    // Unique identifier for the soil sensor
    private int sensorId;
    // Moisture level measured by the sensor
    private float moistureLevel;
    // Nutrients level measured by the sensor
    private float nutrientsLevel;
    // Temperature level measured by the sensor
    private float temperatureLevel;
    // Salinity level measured by the sensor
    private float salinityLevel;
    // Identifier for the garden associated with the sensor
    private int gardenId;
    // API identifier for the sensor
    private int apiId;

    /**
     * Default constructor for SoilSensor
     */
    public SoilSensor() {
    }

    /**
     * Constructor for SoilSensor with specific attributes
     *
     * @param sensorId         Unique identifier for the sensor
     * @param moistureLevel    Moisture level measured by the sensor
     * @param nutrientsLevel   Nutrients level measured by the sensor
     * @param temperatureLevel Temperature level measured by the sensor
     * @param salinityLevel    Salinity level measured by the sensor
     * @param gardenId         Identifier for the garden associated with the sensor
     * @param apiId            API identifier for the sensor
     */
    public SoilSensor(int sensorId, float moistureLevel, float nutrientsLevel, float temperatureLevel, float salinityLevel, int gardenId, int apiId) {
        this.sensorId = sensorId;
        this.moistureLevel = moistureLevel;
        this.nutrientsLevel = nutrientsLevel;
        this.temperatureLevel = temperatureLevel;
        this.salinityLevel = salinityLevel;
        this.gardenId = gardenId;
        this.apiId = apiId;
    }

    // Getters and Setters
    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public float getMoistureLevel() {
        return moistureLevel;
    }

    public void setMoistureLevel(float moistureLevel) {
        this.moistureLevel = moistureLevel;
    }

    public float getNutrientsLevel() {
        return nutrientsLevel;
    }

    public void setNutrientsLevel(float nutrientsLevel) {
        this.nutrientsLevel = nutrientsLevel;
    }

    public float getTemperatureLevel() {
        return temperatureLevel;
    }

    public void setTemperatureLevel(float temperatureLevel) {
        this.temperatureLevel = temperatureLevel;
    }

    public float getSalinityLevel() {
        return salinityLevel;
    }

    public void setSalinityLevel(float salinityLevel) {
        this.salinityLevel = salinityLevel;
    }

    public int getGardenId() {
        return gardenId;
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
    }

    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    /**
     * Constructor for SoilSensor from Parcel
     *
     * @param in Parcel containing the SoilSensor data
     */
    protected SoilSensor(Parcel in) {
        sensorId = in.readInt();
        moistureLevel = in.readFloat();
        nutrientsLevel = in.readFloat();
        temperatureLevel = in.readFloat();
        salinityLevel = in.readFloat();
        gardenId = in.readInt();
        apiId = in.readInt();
    }

    /**
     * Creator for SoilSensor
     */
    public static final Creator<SoilSensor> CREATOR = new Creator<SoilSensor>() {
        @Override
        public SoilSensor createFromParcel(Parcel in) {
            return new SoilSensor(in);
        }

        @Override
        public SoilSensor[] newArray(int size) {
            return new SoilSensor[size];
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
     * Write the SoilSensor data to the parcel
     *
     * @param parcel Parcel to write the data to
     * @param i      Additional flags
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(sensorId);
        parcel.writeFloat(moistureLevel);
        parcel.writeFloat(nutrientsLevel);
        parcel.writeFloat(temperatureLevel);
        parcel.writeFloat(salinityLevel);
        parcel.writeInt(gardenId);
        parcel.writeInt(apiId);
    }
}