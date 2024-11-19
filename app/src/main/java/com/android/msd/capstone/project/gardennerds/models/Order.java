package com.android.msd.capstone.project.gardennerds.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class for Order
 */
public class Order implements Parcelable {

    // Order fields...
    private int orderId;
    private int userId;
    private String purchaseDate;
    private String paymentType;
    private String deliveryStatus;
    private float amount;
    private int marketApiId;
    private int suggestionId;

    public Order() {
    }

    /**
     * Constructor for Order
     */
    public Order(int orderId, int userId, String purchaseDate, String paymentType, String deliveryStatus, float amount, int marketApiId, int suggestionId) {
        this.orderId = orderId;
        this.userId = userId;
        this.purchaseDate = purchaseDate;
        this.paymentType = paymentType;
        this.deliveryStatus = deliveryStatus;
        this.amount = amount;
        this.marketApiId = marketApiId;
        this.suggestionId = suggestionId;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getMarketApiId() {
        return marketApiId;
    }

    public void setMarketApiId(int marketApiId) {
        this.marketApiId = marketApiId;
    }

    public int getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(int suggestionId) {
        this.suggestionId = suggestionId;
    }

    /**
     * Constructor for Order
     */
    protected Order(Parcel in) {
        orderId = in.readInt();
        userId = in.readInt();
        purchaseDate = in.readString();
        paymentType = in.readString();
        deliveryStatus = in.readString();
        amount = in.readFloat();
        marketApiId = in.readInt();
        suggestionId = in.readInt();
    }

    /**
     * Creator for Order
     */
    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
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
        parcel.writeInt(orderId);
        parcel.writeInt(userId);
        parcel.writeString(purchaseDate);
        parcel.writeString(paymentType);
        parcel.writeString(deliveryStatus);
        parcel.writeFloat(amount);
        parcel.writeInt(marketApiId);
        parcel.writeInt(suggestionId);
    }
}