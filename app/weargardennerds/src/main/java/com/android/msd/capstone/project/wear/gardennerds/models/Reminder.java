package com.android.msd.capstone.project.wear.gardennerds.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class for Reminder
 */
public class Reminder implements Parcelable {

    // Reminder fields...
    private int reminderId;
    private String message;
    private String dateTime;
    private int plantId;
    private int reminderTypeId;
    private String frequency;
    private String moistureLevel;
    private String temperatureLevel;
    private String sunlightLevel;
    private String nutrientRequired;
    private String reminderTime;

    /**
     * Constructor for Reminder
     */
    public Reminder() {
    }


    // Getters and Setters
    public int getReminderId() {
        return reminderId;
    }

    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getPlantId() {
        return plantId;
    }

    public void setPlantId(int plantId) {
        this.plantId = plantId;
    }

    public int getReminderTypeId() {
        return reminderTypeId;
    }

    public void setReminderTypeId(int reminderTypeId) {
        this.reminderTypeId = reminderTypeId;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getMoistureLevel() {
        return moistureLevel;
    }

    public void setMoistureLevel(String moistureLevel) {
        this.moistureLevel = moistureLevel;
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

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    /**
     * Constructor for Reminder
     */
    protected Reminder(Parcel in) {
        reminderId = in.readInt();
        message = in.readString();
        dateTime = in.readString();
        plantId = in.readInt();
        reminderTypeId = in.readInt();
        frequency = in.readString();
        moistureLevel = in.readString();
        temperatureLevel = in.readString();
        sunlightLevel = in.readString();
        nutrientRequired = in.readString();
        reminderTime = in.readString();
    }

    /**
     * Creator for Reminder
     */
    public static final Creator<Reminder> CREATOR = new Creator<Reminder>() {
        @Override
        public Reminder createFromParcel(Parcel in) {
            return new Reminder(in);
        }

        @Override
        public Reminder[] newArray(int size) {
            return new Reminder[size];
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
        parcel.writeInt(reminderId);
        parcel.writeString(message);
        parcel.writeString(dateTime);
        parcel.writeInt(plantId);
        parcel.writeInt(reminderTypeId);
        parcel.writeString(frequency);
        parcel.writeString(moistureLevel);
        parcel.writeString(temperatureLevel);
        parcel.writeString(sunlightLevel);
        parcel.writeString(nutrientRequired);
        parcel.writeString(reminderTime);
    }
}