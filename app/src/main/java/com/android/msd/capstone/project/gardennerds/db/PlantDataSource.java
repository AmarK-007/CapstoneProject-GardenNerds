package com.android.msd.capstone.project.gardennerds.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.msd.capstone.project.gardennerds.models.Plant;

import java.util.ArrayList;
import java.util.List;

/**
 * DataSource class for Plant
 */
public class PlantDataSource {
    private DBHelper dbHelper;

    /**
     * Constructor for PlantDataSource
     *
     * @param context
     * @return
     */
    public PlantDataSource(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    // Plant table name
    public static final String TABLE_NAME = "plants";
    public static final String COLUMN_PLANT_ID = "plant_id";
    public static final String COLUMN_GARDEN_ID = "garden_id";
    public static final String COLUMN_PLANT_NAME = "plant_name";
    public static final String COLUMN_PLANT_TYPE = "plant_type";
    public static final String COLUMN_GROWTH_CONDITIONS = "growth_conditions";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_PLANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_GARDEN_ID + " INTEGER,"
                    + COLUMN_PLANT_NAME + " TEXT,"
                    + COLUMN_PLANT_TYPE + " TEXT,"
                    + COLUMN_GROWTH_CONDITIONS + " TEXT,"
                    + "FOREIGN KEY(" + COLUMN_GARDEN_ID + ") REFERENCES gardens(garden_id)"
                    + ")";

    /**
     * insertPlant method
     *
     * @param plant
     * @return
     */
    public boolean insertPlant(Plant plant) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_GARDEN_ID, plant.getGardenId());
        values.put(COLUMN_PLANT_NAME, plant.getPlantName());
        values.put(COLUMN_PLANT_TYPE, plant.getPlantType());
        values.put(COLUMN_GROWTH_CONDITIONS, plant.getGrowthConditions());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result != -1; // Return true if insertion was successful, false otherwise
    }

    /**
     * getPlant method
     *
     * @param plantId
     * @return
     */
    @SuppressLint("Range")
    public Plant getPlant(int plantId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                null,
                COLUMN_PLANT_ID + "=?",
                new String[]{String.valueOf(plantId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Plant plant = new Plant();
            plant.setPlantId(cursor.getInt(cursor.getColumnIndex(COLUMN_PLANT_ID)));
            plant.setGardenId(cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID)));
            plant.setPlantName(cursor.getString(cursor.getColumnIndex(COLUMN_PLANT_NAME)));
            plant.setPlantType(cursor.getString(cursor.getColumnIndex(COLUMN_PLANT_TYPE)));
            plant.setGrowthConditions(cursor.getString(cursor.getColumnIndex(COLUMN_GROWTH_CONDITIONS)));

            cursor.close();
            return plant;
        } else {
            return null;
        }
    }

    /**
     * getAllPlants method
     *
     * @return
     */
    @SuppressLint("Range")
    public List<Plant> getAllPlants() {
        List<Plant> plants = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Plant plant = new Plant();
                plant.setPlantId(cursor.getInt(cursor.getColumnIndex(COLUMN_PLANT_ID)));
                plant.setGardenId(cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID)));
                plant.setPlantName(cursor.getString(cursor.getColumnIndex(COLUMN_PLANT_NAME)));
                plant.setPlantType(cursor.getString(cursor.getColumnIndex(COLUMN_PLANT_TYPE)));
                plant.setGrowthConditions(cursor.getString(cursor.getColumnIndex(COLUMN_GROWTH_CONDITIONS)));

                plants.add(plant);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return plants;
    }

    /**
     * updatePlant method
     *
     * @param plant
     * @return
     */
    public int updatePlant(Plant plant) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_GARDEN_ID, plant.getGardenId());
        values.put(COLUMN_PLANT_NAME, plant.getPlantName());
        values.put(COLUMN_PLANT_TYPE, plant.getPlantType());
        values.put(COLUMN_GROWTH_CONDITIONS, plant.getGrowthConditions());

        return db.update(TABLE_NAME, values, COLUMN_PLANT_ID + " = ?",
                new String[]{String.valueOf(plant.getPlantId())});
    }

    /**
     * deletePlant method
     *
     * @param plant
     * @return
     */
    public void deletePlant(Plant plant) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_PLANT_ID + " = ?",
                new String[]{String.valueOf(plant.getPlantId())});
        db.close();
    }
}