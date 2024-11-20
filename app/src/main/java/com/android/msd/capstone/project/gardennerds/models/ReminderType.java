package com.android.msd.capstone.project.gardennerds.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class for ReminderType
 */
public class ReminderType implements Parcelable {

    // ReminderType fields...
    private int reminderTypeId;
    private String reminderTypeName;
    private String description;

    public ReminderType() {
    }

    /**
     * Constructor for ReminderType
     */
    public ReminderType(int reminderTypeId, String reminderTypeName, String description) {
        this.reminderTypeId = reminderTypeId;
        this.reminderTypeName = reminderTypeName;
        this.description = description;
    }

    // Getters and Setters
    public int getReminderTypeId() {
        return reminderTypeId;
    }

    public void setReminderTypeId(int reminderTypeId) {
        this.reminderTypeId = reminderTypeId;
    }

    public String getReminderTypeName() {
        return reminderTypeName;
    }

    public void setReminderTypeName(String reminderTypeName) {
        this.reminderTypeName = reminderTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Constructor for ReminderType
     */
    protected ReminderType(Parcel in) {
        reminderTypeId = in.readInt();
        reminderTypeName = in.readString();
        description = in.readString();
    }

    /**
     * Creator for ReminderType
     */
    public static final Creator<ReminderType> CREATOR = new Creator<ReminderType>() {
        @Override
        public ReminderType createFromParcel(Parcel in) {
            return new ReminderType(in);
        }

        @Override
        public ReminderType[] newArray(int size) {
            return new ReminderType[size];
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
        parcel.writeInt(reminderTypeId);
        parcel.writeString(reminderTypeName);
        parcel.writeString(description);
    }
}