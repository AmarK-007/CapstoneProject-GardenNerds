package com.android.msd.capstone.project.gardennerds.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class for ProductSuggestion
 */
public class ProductSuggestion implements Parcelable {

    // ProductSuggestion fields...
    private int suggestionId;
    private int gardenId;
    private int marketApiId;

    public ProductSuggestion() {
    }

    /**
     * Constructor for ProductSuggestion
     */
    public ProductSuggestion(int suggestionId, int gardenId, int marketApiId) {
        this.suggestionId = suggestionId;
        this.gardenId = gardenId;
        this.marketApiId = marketApiId;
    }

    // Getters and Setters
    public int getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(int suggestionId) {
        this.suggestionId = suggestionId;
    }

    public int getGardenId() {
        return gardenId;
    }

    public void setGardenId(int gardenId) {
        this.gardenId = gardenId;
    }

    public int getMarketApiId() {
        return marketApiId;
    }

    public void setMarketApiId(int marketApiId) {
        this.marketApiId = marketApiId;
    }

    /**
     * Constructor for ProductSuggestion
     */
    protected ProductSuggestion(Parcel in) {
        suggestionId = in.readInt();
        gardenId = in.readInt();
        marketApiId = in.readInt();
    }

    /**
     * Creator for ProductSuggestion
     */
    public static final Creator<ProductSuggestion> CREATOR = new Creator<ProductSuggestion>() {
        @Override
        public ProductSuggestion createFromParcel(Parcel in) {
            return new ProductSuggestion(in);
        }

        @Override
        public ProductSuggestion[] newArray(int size) {
            return new ProductSuggestion[size];
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
        parcel.writeInt(suggestionId);
        parcel.writeInt(gardenId);
        parcel.writeInt(marketApiId);
    }
}