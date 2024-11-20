package com.android.msd.capstone.project.gardennerds.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class for Garden
 */
public class Garden implements Parcelable {

    // Garden fields...
    private int gardenId;
    private float location;
    private String areaMeasurement;
    private int userId;

    public Garden() {
    }

    /**
     * Constructor for Garden
     */
    public Garden(int gardenId, float location, String areaMeasurement, int userId) {
        this.gardenId = gardenId;
        this.location = location;
        this.areaMeasurement = areaMeasurement;
        this.userId = userId;
    }

    // Getters and Setters
    public int getGardenId() {
        return gardenId;
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
    }

    public float getLocation() {
        return location;
    }

    public void setLocation(float location) {
        this.location = location;
    }

    public String getAreaMeasurement() {
        return areaMeasurement;
    }

    public void setAreaMeasurement(String areaMeasurement) {
        this.areaMeasurement = areaMeasurement;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Constructor for Garden
     */
    protected Garden(Parcel in) {
        gardenId = in.readInt();
        location = in.readFloat();
        areaMeasurement = in.readString();
        userId = in.readInt();
    }

    /**
     * Creator for Garden
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
        parcel.writeInt(gardenId);
        parcel.writeFloat(location);
        parcel.writeString(areaMeasurement);
        parcel.writeInt(userId);
    }
}