package com.android.msd.capstone.project.gardennerds.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.msd.capstone.project.gardennerds.models.SoilDataApi;

import java.util.ArrayList;
import java.util.List;

/**
 * DataSource class for SoilDataApi
 */
public class SoilDataApiDataSource {
    private DBHelper dbHelper;

    /**
     * Constructor for SoilDataApiDataSource
     *
     * @param context
     * @return
     */
    public SoilDataApiDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    // SoilDataApi table name
    public static final String TABLE_NAME = "soil_data_api";
    public static final String COLUMN_API_ID = "api_id";
    public static final String COLUMN_API_URL = "api_url";
    public static final String COLUMN_API_KEY = "api_key";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_API_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_API_URL + " TEXT,"
                    + COLUMN_API_KEY + " TEXT"
                    + ")";

    /**
     * insertSoilDataApi method
     *
     * @param soilDataApi
     * @return
     */
    public boolean insertSoilDataApi(SoilDataApi soilDataApi) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_API_URL, soilDataApi.getApiUrl());
        values.put(COLUMN_API_KEY, soilDataApi.getApiKey());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result != -1; // Return true if insertion was successful, false otherwise
    }

    /**
     * getSoilDataApi method
     *
     * @param apiId
     * @return
     */
    @SuppressLint("Range")
    public SoilDataApi getSoilDataApi(int apiId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                null,
                COLUMN_API_ID + "=?",
                new String[]{String.valueOf(apiId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            SoilDataApi soilDataApi = new SoilDataApi();
            soilDataApi.setApiId(cursor.getInt(cursor.getColumnIndex(COLUMN_API_ID)));
            soilDataApi.setApiUrl(cursor.getString(cursor.getColumnIndex(COLUMN_API_URL)));
            soilDataApi.setApiKey(cursor.getString(cursor.getColumnIndex(COLUMN_API_KEY)));

            cursor.close();
            return soilDataApi;
        } else {
            return null;
        }
    }

    /**
     * getAllSoilDataApis method
     *
     * @return
     */
    @SuppressLint("Range")
    public List<SoilDataApi> getAllSoilDataApis() {
        List<SoilDataApi> soilDataApis = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SoilDataApi soilDataApi = new SoilDataApi();
                soilDataApi.setApiId(cursor.getInt(cursor.getColumnIndex(COLUMN_API_ID)));
                soilDataApi.setApiUrl(cursor.getString(cursor.getColumnIndex(COLUMN_API_URL)));
                soilDataApi.setApiKey(cursor.getString(cursor.getColumnIndex(COLUMN_API_KEY)));

                soilDataApis.add(soilDataApi);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return soilDataApis;
    }

    /**
     * updateSoilDataApi method
     *
     * @param soilDataApi
     * @return
     */
    public int updateSoilDataApi(SoilDataApi soilDataApi) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_API_URL, soilDataApi.getApiUrl());
        values.put(COLUMN_API_KEY, soilDataApi.getApiKey());

        return db.update(TABLE_NAME, values, COLUMN_API_ID + " = ?",
                new String[]{String.valueOf(soilDataApi.getApiId())});
    }

    /**
     * deleteSoilDataApi method
     *
     * @param soilDataApi
     * @return
     */
    public void deleteSoilDataApi(SoilDataApi soilDataApi) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_API_ID + " = ?",
                new String[]{String.valueOf(soilDataApi.getApiId())});
        db.close();
    }
}
