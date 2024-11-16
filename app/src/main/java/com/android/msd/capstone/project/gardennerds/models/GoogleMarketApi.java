package com.android.msd.capstone.project.gardennerds.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class for GoogleMarketApi
 */
public class GoogleMarketApi implements Parcelable {

    // GoogleMarketApi fields...
    private int marketApiId;
    private String apiUrl;

    public GoogleMarketApi() {
    }

    /**
     * Constructor for GoogleMarketApi
     */
    public GoogleMarketApi(int marketApiId, String apiUrl) {
        this.marketApiId = marketApiId;
        this.apiUrl = apiUrl;
    }

    // Getters and Setters
    public int getMarketApiId() {
        return marketApiId;
    }

    public void setMarketApiId(int marketApiId) {
        this.marketApiId = marketApiId;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    /**
     * Constructor for GoogleMarketApi
     */
    protected GoogleMarketApi(Parcel in) {
        marketApiId = in.readInt();
        apiUrl = in.readString();
    }

    /**
     * Creator for GoogleMarketApi
     */
    public static final Creator<GoogleMarketApi> CREATOR = new Creator<GoogleMarketApi>() {
        @Override
        public GoogleMarketApi createFromParcel(Parcel in) {
            return new GoogleMarketApi(in);
        }

        @Override
        public GoogleMarketApi[] newArray(int size) {
            return new GoogleMarketApi[size];
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
        parcel.writeInt(marketApiId);
        parcel.writeString(apiUrl);
    }
}