package com.android.msd.capstone.project.gardennerds.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class for SoilDataApi
 */
public class SoilDataApi implements Parcelable {

    // SoilDataApi fields...
    private int apiId;
    private String apiUrl;
    private String apiKey;

    public SoilDataApi() {
    }

    /**
     * Constructor for SoilDataApi
     */
    public SoilDataApi(int apiId, String apiUrl, String apiKey) {
        this.apiId = apiId;
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    // Getters and Setters
    public int getApiId() {
        return apiId;
    }

    public void setApiId(int apiId) {
        this.apiId = apiId;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Constructor for SoilDataApi
     */
    protected SoilDataApi(Parcel in) {
        apiId = in.readInt();
        apiUrl = in.readString();
        apiKey = in.readString();
    }

    /**
     * Creator for SoilDataApi
     */
    public static final Creator<SoilDataApi> CREATOR = new Creator<SoilDataApi>() {
        @Override
        public SoilDataApi createFromParcel(Parcel in) {
            return new SoilDataApi(in);
        }

        @Override
        public SoilDataApi[] newArray(int size) {
            return new SoilDataApi[size];
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
        parcel.writeInt(apiId);
        parcel.writeString(apiUrl);
        parcel.writeString(apiKey);
    }
}