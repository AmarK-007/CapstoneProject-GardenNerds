package com.android.msd.capstone.project.wear.gardennerds.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class for Reminder
 */
public class Reminder implements Parcelable {

    // Unique identifier for the reminder
    private int reminderId;
    // Message for the reminder
    private String message;
    // Date and time for the reminder
    private String dateTime;
    // Identifier for the plant associated with the reminder
    private int plantId;
    // Identifier for the type of reminder
    private int reminderTypeId;
    // Frequency of the reminder
    private String frequency;
    // Moisture level required for the plant
    private String moistureLevel;
    // Temperature level required for the plant
    private String temperatureLevel;
    // Sunlight level required for the plant
    private String sunlightLevel;
    // Nutrients required for the plant
    private String nutrientRequired;
    // Time for the reminder
    private String reminderTime;
    // Name of the plant associated with the reminder
    private String plantName;

    /**
     * Default constructor for Reminder
     */
    public Reminder() {
    }

    /**
     * Constructor for Reminder with specific attributes
     *
     * @param plantName        Name of the plant
     * @param frequency        Frequency of the reminder
     * @param reminderTypeId   Type of the reminder
     * @param reminderTime     Time for the reminder
     */
    public Reminder(String plantName, String frequency, int reminderTypeId, String reminderTime) {
        this.plantName = plantName;
        this.frequency = frequency;
        this.reminderTypeId = reminderTypeId;
        this.reminderTime = reminderTime;
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

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    /**
     * Constructor for Reminder from Parcel
     *
     * @param in Parcel containing the Reminder data
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
        plantName = in.readString();
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
     * Describe the contents of the parcel
     *
     * @return int
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write the Reminder data to the parcel
     *
     * @param parcel Parcel to write the data to
     * @param i      Additional flags
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
        parcel.writeString(plantName);
    }
}