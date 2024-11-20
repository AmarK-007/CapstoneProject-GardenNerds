package com.android.msd.capstone.project.gardennerds.models;

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

    public Reminder() {
    }

    /**
     * Constructor for Reminder
     */
    public Reminder(int reminderId, String message, String dateTime, int plantId, int reminderTypeId) {
        this.reminderId = reminderId;
        this.message = message;
        this.dateTime = dateTime;
        this.plantId = plantId;
        this.reminderTypeId = reminderTypeId;
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

    /**
     * Constructor for Reminder
     */
    protected Reminder(Parcel in) {
        reminderId = in.readInt();
        message = in.readString();
        dateTime = in.readString();
        plantId = in.readInt();
        reminderTypeId = in.readInt();
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
    }
}