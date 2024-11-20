package com.android.msd.capstone.project.gardennerds.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.android.msd.capstone.project.gardennerds.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * DataSource class for User
 */
public class UserDataSource {
    private DBHelper dbHelper;

    /**
     * Constructor for UserDataSource
     *
     * @param context
     * @return
     */
    public UserDataSource(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    // User table name
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PURCHASE_HISTORY = "purchase_history";
    public static final String COLUMN_SHIPPING_ADDRESS_1 = "shipping_address_1";
    public static final String COLUMN_SHIPPING_ADDRESS_2 = "shipping_address_2";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_PROVINCE = "province";
    public static final String COLUMN_PINCODE = "pincode";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_EMAIL + " TEXT UNIQUE,"
                    + COLUMN_PASSWORD + " TEXT,"
                    + COLUMN_USERNAME + " TEXT,"
                    + COLUMN_PURCHASE_HISTORY + " TEXT,"
                    + COLUMN_SHIPPING_ADDRESS_1 + " TEXT,"
                    + COLUMN_SHIPPING_ADDRESS_2 + " TEXT,"
                    + COLUMN_CITY + " TEXT,"
                    + COLUMN_PROVINCE + " TEXT,"
                    + COLUMN_PINCODE + " TEXT"
                    + ")";

    /**
     * insertUser method
     *
     * @param user
     * @return
     */
    public boolean insertUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PURCHASE_HISTORY, user.getPurchaseHistory());
        values.put(COLUMN_SHIPPING_ADDRESS_1, user.getShippingAddress1());
        values.put(COLUMN_SHIPPING_ADDRESS_2, user.getShippingAddress2());
        values.put(COLUMN_CITY, user.getCity());
        values.put(COLUMN_PROVINCE, user.getProvince());
        values.put(COLUMN_PINCODE, user.getPincode());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        if (result == -1) {
            return false; // Insertion failed
        } else {
            return true; // Insertion successful
        }
    }

    /**
     * getUser method
     *
     * @param username
     * @param password
     * @return
     */
    @SuppressLint("Range")
    public User getUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                null,
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            User user = new User();
            user.setUserId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
            user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
            user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
            user.setPurchaseHistory(cursor.getString(cursor.getColumnIndex(COLUMN_PURCHASE_HISTORY)));
            user.setShippingAddress1(cursor.getString(cursor.getColumnIndex(COLUMN_SHIPPING_ADDRESS_1)));
            user.setShippingAddress2(cursor.getString(cursor.getColumnIndex(COLUMN_SHIPPING_ADDRESS_2)));
            user.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
            user.setProvince(cursor.getString(cursor.getColumnIndex(COLUMN_PROVINCE)));
            user.setPincode(cursor.getString(cursor.getColumnIndex(COLUMN_PINCODE)));

            cursor.close();
            return user;
        } else {
            return null;
        }
    }

    /**
     * getAllUsers method
     *
     * @return
     */
    @SuppressLint("Range")
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUserId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)));
                user.setPurchaseHistory(cursor.getString(cursor.getColumnIndex(COLUMN_PURCHASE_HISTORY)));
                user.setShippingAddress1(cursor.getString(cursor.getColumnIndex(COLUMN_SHIPPING_ADDRESS_1)));
                user.setShippingAddress2(cursor.getString(cursor.getColumnIndex(COLUMN_SHIPPING_ADDRESS_2)));
                user.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
                user.setProvince(cursor.getString(cursor.getColumnIndex(COLUMN_PROVINCE)));
                user.setPincode(cursor.getString(cursor.getColumnIndex(COLUMN_PINCODE)));

                users.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return users;
    }

    /**
     * updateUser method
     *
     * @param user
     * @return
     */
    public int updateUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PURCHASE_HISTORY, user.getPurchaseHistory());
        values.put(COLUMN_SHIPPING_ADDRESS_1, user.getShippingAddress1());
        values.put(COLUMN_SHIPPING_ADDRESS_2, user.getShippingAddress2());
        values.put(COLUMN_CITY, user.getCity());
        values.put(COLUMN_PROVINCE, user.getProvince());
        values.put(COLUMN_PINCODE, user.getPincode());

        return db.update(TABLE_NAME, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getUserId())});
    }

    /**
     * deleteUser method
     *
     * @param user
     * @return
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getUserId())});
        db.close();
    }

    /**
     * validateUser method
     *
     * @param username
     * @param password
     * @return
     */
    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_USERNAME},
                COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}
