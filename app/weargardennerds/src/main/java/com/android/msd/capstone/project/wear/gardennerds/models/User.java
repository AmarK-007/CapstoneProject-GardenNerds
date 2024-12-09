package com.android.msd.capstone.project.wear.gardennerds.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class for User
 */
public class User implements Parcelable {

    // Unique identifier for the user
    private int userId;
    // Name of the user
    private String name;
    // Email address of the user
    private String email;
    // Password for the user's account
    private String password;
    // Username for the user's account
    private String username;
    // Purchase history of the user
    private String purchaseHistory;
    // First line of the user's shipping address
    private String shippingAddress1;
    // Second line of the user's shipping address
    private String shippingAddress2;
    // City of the user's shipping address
    private String city;
    // Province of the user's shipping address
    private String province;
    // Pincode of the user's shipping address
    private String pincode;

    /**
     * Default constructor for User
     */
    public User() {
    }

    /**
     * Constructor for User with specific attributes
     *
     * @param userId           Unique identifier for the user
     * @param name             Name of the user
     * @param email            Email address of the user
     * @param password         Password for the user's account
     * @param username         Username for the user's account
     * @param purchaseHistory  Purchase history of the user
     * @param shippingAddress1 First line of the user's shipping address
     * @param shippingAddress2 Second line of the user's shipping address
     * @param city             City of the user's shipping address
     * @param province         Province of the user's shipping address
     * @param pincode          Pincode of the user's shipping address
     */
    public User(int userId, String name, String email, String password, String username, String purchaseHistory, String shippingAddress1, String shippingAddress2, String city, String province, String pincode) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.purchaseHistory = purchaseHistory;
        this.shippingAddress1 = shippingAddress1;
        this.shippingAddress2 = shippingAddress2;
        this.city = city;
        this.province = province;
        this.pincode = pincode;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(String purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }

    public String getShippingAddress1() {
        return shippingAddress1;
    }

    public void setShippingAddress1(String shippingAddress1) {
        this.shippingAddress1 = shippingAddress1;
    }

    public String getShippingAddress2() {
        return shippingAddress2;
    }

    public void setShippingAddress2(String shippingAddress2) {
        this.shippingAddress2 = shippingAddress2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    /**
     * Constructor for User from Parcel
     *
     * @param in Parcel containing the User data
     */
    protected User(Parcel in) {
        userId = in.readInt();
        name = in.readString();
        email = in.readString();
        password = in.readString();
        username = in.readString();
        purchaseHistory = in.readString();
        shippingAddress1 = in.readString();
        shippingAddress2 = in.readString();
        city = in.readString();
        province = in.readString();
        pincode = in.readString();
    }

    /**
     * Creator for User
     */
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
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
     * Write the User data to the parcel
     *
     * @param parcel Parcel to write the data to
     * @param i      Additional flags
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(username);
        parcel.writeString(purchaseHistory);
        parcel.writeString(shippingAddress1);
        parcel.writeString(shippingAddress2);
        parcel.writeString(city);
        parcel.writeString(province);
        parcel.writeString(pincode);
    }
}