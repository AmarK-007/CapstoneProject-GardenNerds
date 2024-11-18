package com.android.msd.capstone.project.gardennerds.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.msd.capstone.project.gardennerds.models.SoilSensor;

import java.util.ArrayList;
import java.util.List;

/**
 * DataSource class for SoilSensor
 */
public class SoilSensorDataSource {
    private DBHelper dbHelper;

    /**
     * Constructor for SoilSensorDataSource
     *
     * @param context
     * @return
     */
    public SoilSensorDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    // SoilSensor table name
    public static final String TABLE_NAME = "soil_sensors";
    public static final String COLUMN_SENSOR_ID = "sensor_id";
    public static final String COLUMN_MOISTURE_LEVEL = "moisture_level";
    public static final String COLUMN_NUTRIENTS_LEVEL = "nutrients_level";
    public static final String COLUMN_TEMPERATURE_LEVEL = "temperature_level";
    public static final String COLUMN_SALINITY_LEVEL = "salinity_level";
    public static final String COLUMN_GARDEN_ID = "garden_id";
    public static final String COLUMN_API_ID = "api_id";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_SENSOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_MOISTURE_LEVEL + " FLOAT,"
                    + COLUMN_NUTRIENTS_LEVEL + " FLOAT,"
                    + COLUMN_TEMPERATURE_LEVEL + " FLOAT,"
                    + COLUMN_SALINITY_LEVEL + " FLOAT,"
                    + COLUMN_GARDEN_ID + " INTEGER,"
                    + COLUMN_API_ID + " INTEGER,"
                    + "FOREIGN KEY(" + COLUMN_GARDEN_ID + ") REFERENCES gardens(garden_id),"
                    + "FOREIGN KEY(" + COLUMN_API_ID + ") REFERENCES soil_data_api(api_id)"
                    + ")";

    /**
     * insertSoilSensor method
     *
     * @param soilSensor
     * @return
     */
    public boolean insertSoilSensor(SoilSensor soilSensor) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MOISTURE_LEVEL, soilSensor.getMoistureLevel());
        values.put(COLUMN_NUTRIENTS_LEVEL, soilSensor.getNutrientsLevel());
        values.put(COLUMN_TEMPERATURE_LEVEL, soilSensor.getTemperatureLevel());
        values.put(COLUMN_SALINITY_LEVEL, soilSensor.getSalinityLevel());
        values.put(COLUMN_GARDEN_ID, soilSensor.getGardenId());
        values.put(COLUMN_API_ID, soilSensor.getApiId());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result != -1; // Return true if insertion was successful, false otherwise
    }

    /**
     * getSoilSensor method
     *
     * @param sensorId
     * @return
     */
    @SuppressLint("Range")
    public SoilSensor getSoilSensor(int sensorId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                null,
                COLUMN_SENSOR_ID + "=?",
                new String[]{String.valueOf(sensorId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            SoilSensor soilSensor = new SoilSensor();
            soilSensor.setSensorId(cursor.getInt(cursor.getColumnIndex(COLUMN_SENSOR_ID)));
            soilSensor.setMoistureLevel(cursor.getFloat(cursor.getColumnIndex(COLUMN_MOISTURE_LEVEL)));
            soilSensor.setNutrientsLevel(cursor.getFloat(cursor.getColumnIndex(COLUMN_NUTRIENTS_LEVEL)));
            soilSensor.setTemperatureLevel(cursor.getFloat(cursor.getColumnIndex(COLUMN_TEMPERATURE_LEVEL)));
            soilSensor.setSalinityLevel(cursor.getFloat(cursor.getColumnIndex(COLUMN_SALINITY_LEVEL)));
            soilSensor.setGardenId(cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID)));
            soilSensor.setApiId(cursor.getInt(cursor.getColumnIndex(COLUMN_API_ID)));

            cursor.close();
            return soilSensor;
        } else {
            return null;
        }
    }

    /**
     * getAllSoilSensors method
     *
     * @return
     */
    @SuppressLint("Range")
    public List<SoilSensor> getAllSoilSensors() {
        List<SoilSensor> soilSensors = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SoilSensor soilSensor = new SoilSensor();
                soilSensor.setSensorId(cursor.getInt(cursor.getColumnIndex(COLUMN_SENSOR_ID)));
                soilSensor.setMoistureLevel(cursor.getFloat(cursor.getColumnIndex(COLUMN_MOISTURE_LEVEL)));
                soilSensor.setNutrientsLevel(cursor.getFloat(cursor.getColumnIndex(COLUMN_NUTRIENTS_LEVEL)));
                soilSensor.setTemperatureLevel(cursor.getFloat(cursor.getColumnIndex(COLUMN_TEMPERATURE_LEVEL)));
                soilSensor.setSalinityLevel(cursor.getFloat(cursor.getColumnIndex(COLUMN_SALINITY_LEVEL)));
                soilSensor.setGardenId(cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID)));
                soilSensor.setApiId(cursor.getInt(cursor.getColumnIndex(COLUMN_API_ID)));

                soilSensors.add(soilSensor);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return soilSensors;
    }

    /**
     * updateSoilSensor method
     *
     * @param soilSensor
     * @return
     */
    public int updateSoilSensor(SoilSensor soilSensor) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MOISTURE_LEVEL, soilSensor.getMoistureLevel());
        values.put(COLUMN_NUTRIENTS_LEVEL, soilSensor.getNutrientsLevel());
        values.put(COLUMN_TEMPERATURE_LEVEL, soilSensor.getTemperatureLevel());
        values.put(COLUMN_SALINITY_LEVEL, soilSensor.getSalinityLevel());
        values.put(COLUMN_GARDEN_ID, soilSensor.getGardenId());
        values.put(COLUMN_API_ID, soilSensor.getApiId());

        return db.update(TABLE_NAME, values, COLUMN_SENSOR_ID + " = ?",
                new String[]{String.valueOf(soilSensor.getSensorId())});
    }

    /**
     * deleteSoilSensor method
     *
     * @param soilSensor
     * @return
     */
    public void deleteSoilSensor(SoilSensor soilSensor) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_SENSOR_ID + " = ?",
                new String[]{String.valueOf(soilSensor.getSensorId())});
        db.close();
    }
}