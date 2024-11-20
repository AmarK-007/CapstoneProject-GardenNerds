package com.android.msd.capstone.project.gardennerds.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.msd.capstone.project.gardennerds.models.Garden;

import java.util.ArrayList;
import java.util.List;

/**
 * DataSource class for Garden
 */
public class GardenDataSource {
    private DBHelper dbHelper;

    /**
     * Constructor for GardenDataSource
     *
     * @param context
     * @return
     */
    public GardenDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Garden table name
    public static final String TABLE_NAME = "gardens";
    public static final String COLUMN_GARDEN_ID = "garden_id";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_AREA_MEASUREMENT = "area_measurement";
    public static final String COLUMN_LOCATION_LAT = "latitude";
    public static final String COLUMN_LOCATION_LONG = "longitude";
    public static final String COLUMN_SUNLIGHT_PREFERENCE = "sunlight_preference";
    public static final String COLUMN_WATERING_INTERVAL = "watering_interval";
    public static final String COLUMN_IMAGE_URI = "image";
    public static final String COLUMN_USER_ID = "user_id";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_GARDEN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_AREA_MEASUREMENT + " TEXT,"
                    + COLUMN_LOCATION_LAT + " FLOAT,"
                    + COLUMN_LOCATION_LONG + " FLOAT,"
                    + COLUMN_SUNLIGHT_PREFERENCE + " TEXT,"
                    + COLUMN_WATERING_INTERVAL + " TEXT,"
                    + COLUMN_IMAGE_URI + " TEXT,"
                    + COLUMN_USER_ID + " INTEGER,"
                    + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES users(user_id)"
                    + ")";

    /**
     * insertGarden method
     *
     * @param garden
     * @return
     */
    public boolean insertGarden(Garden garden) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Prepare content values for insertion
        ContentValues values = new ContentValues();
        //values.put(COLUMN_GARDEN_ID, 1);
        values.put(COLUMN_DESCRIPTION, garden.getDescription());
        values.put(COLUMN_AREA_MEASUREMENT, garden.getGardenArea());
        values.put(COLUMN_LOCATION_LAT, garden.getGardenLatitude());
        values.put(COLUMN_LOCATION_LONG, garden.getGardenLongitude());
        values.put(COLUMN_SUNLIGHT_PREFERENCE, garden.getSunlightPreference());
        values.put(COLUMN_WATERING_INTERVAL, garden.getWateringFrequency());
        values.put(COLUMN_IMAGE_URI, garden.getImageUri());
        values.put(COLUMN_USER_ID, 1);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result != -1; // Return true if insertion was successful, false otherwise
    }

    /**
     * getGarden method
     *
     * @param gardenId
     * @return
     */
    @SuppressLint("Range")
    public Garden getGarden(int gardenId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                null,
                COLUMN_GARDEN_ID + "=?",
                new String[]{String.valueOf(gardenId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Garden garden = new Garden();
            garden.setGardenId(cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID)));
            garden.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            garden.setGardenArea(cursor.getString(cursor.getColumnIndex(COLUMN_AREA_MEASUREMENT)));
            garden.setGardenLatitude(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION_LAT)));
            garden.setGardenLongitude(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION_LONG)));
            garden.setSunlightPreference(cursor.getString(cursor.getColumnIndex(COLUMN_SUNLIGHT_PREFERENCE)));
            garden.setWateringFrequency(cursor.getString(cursor.getColumnIndex(COLUMN_WATERING_INTERVAL)));
            garden.setImageUri(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URI)));
            garden.setUserId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));

            cursor.close();
            return garden;
        } else {
            return null;
        }
    }

    /**
     * getAllGardens method
     *
     * @return
     */
    @SuppressLint("Range")
    public List<Garden> getAllGardens() {
        List<Garden> gardens = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Garden garden = new Garden();
                garden.setGardenId(cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID)));
                garden.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                garden.setGardenArea(cursor.getString(cursor.getColumnIndex(COLUMN_AREA_MEASUREMENT)));
                garden.setGardenLatitude(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION_LAT)));
                garden.setGardenLongitude(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION_LONG)));
                garden.setSunlightPreference(cursor.getString(cursor.getColumnIndex(COLUMN_SUNLIGHT_PREFERENCE)));
                garden.setWateringFrequency(cursor.getString(cursor.getColumnIndex(COLUMN_WATERING_INTERVAL)));
                garden.setImageUri(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URI)));
                garden.setUserId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));

                gardens.add(garden);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return gardens;
    }

    /**
     * updateGarden method
     *
     * @param garden
     * @return
     */
    public int updateGarden(Garden garden) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, garden.getDescription());
        values.put(COLUMN_AREA_MEASUREMENT, garden.getGardenArea());
        values.put(COLUMN_LOCATION_LAT, garden.getGardenLatitude());
        values.put(COLUMN_LOCATION_LONG, garden.getGardenLongitude());
        values.put(COLUMN_SUNLIGHT_PREFERENCE, garden.getSunlightPreference());
        values.put(COLUMN_WATERING_INTERVAL, garden.getWateringFrequency());
        values.put(COLUMN_IMAGE_URI, garden.getImageUri());
        values.put(COLUMN_USER_ID, 1);

        return db.update(TABLE_NAME, values, COLUMN_GARDEN_ID + " = ?",
                new String[]{String.valueOf(garden.getGardenId())});
    }

    /**
     * deleteGarden method
     *
     * @param garden
     * @return
     */
    public void deleteGarden(Garden garden) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_GARDEN_ID + " = ?",
                new String[]{String.valueOf(garden.getGardenId())});
        db.close();
    }
}
