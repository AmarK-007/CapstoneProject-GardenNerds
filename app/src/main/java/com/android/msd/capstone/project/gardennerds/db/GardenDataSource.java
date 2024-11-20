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
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_AREA_MEASUREMENT = "area_measurement";
    public static final String COLUMN_USER_ID = "user_id";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_GARDEN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_LOCATION + " FLOAT,"
                    + COLUMN_AREA_MEASUREMENT + " TEXT,"
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

        ContentValues values = new ContentValues();
        values.put(COLUMN_LOCATION, garden.getLocation());
        values.put(COLUMN_AREA_MEASUREMENT, garden.getAreaMeasurement());
        values.put(COLUMN_USER_ID, garden.getUserId());

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
            garden.setLocation(cursor.getFloat(cursor.getColumnIndex(COLUMN_LOCATION)));
            garden.setAreaMeasurement(cursor.getString(cursor.getColumnIndex(COLUMN_AREA_MEASUREMENT)));
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
                garden.setLocation(cursor.getFloat(cursor.getColumnIndex(COLUMN_LOCATION)));
                garden.setAreaMeasurement(cursor.getString(cursor.getColumnIndex(COLUMN_AREA_MEASUREMENT)));
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
        values.put(COLUMN_LOCATION, garden.getLocation());
        values.put(COLUMN_AREA_MEASUREMENT, garden.getAreaMeasurement());
        values.put(COLUMN_USER_ID, garden.getUserId());

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
