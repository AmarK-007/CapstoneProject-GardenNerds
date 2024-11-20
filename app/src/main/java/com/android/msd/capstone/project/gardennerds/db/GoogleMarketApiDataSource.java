package com.android.msd.capstone.project.gardennerds.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.msd.capstone.project.gardennerds.models.GoogleMarketApi;

import java.util.ArrayList;
import java.util.List;

/**
 * DataSource class for GoogleMarketApi
 */
public class GoogleMarketApiDataSource {
    private DBHelper dbHelper;

    /**
     * Constructor for GoogleMarketApiDataSource
     *
     * @param context
     * @return
     */
    public GoogleMarketApiDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    // GoogleMarketApi table name
    public static final String TABLE_NAME = "google_market_api";
    public static final String COLUMN_MARKET_API_ID = "market_api_id";
    public static final String COLUMN_API_URL = "api_url";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_MARKET_API_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_API_URL + " TEXT"
                    + ")";

    /**
     * insertGoogleMarketApi method
     *
     * @param googleMarketApi
     * @return
     */
    public boolean insertGoogleMarketApi(GoogleMarketApi googleMarketApi) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_API_URL, googleMarketApi.getApiUrl());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result != -1; // Return true if insertion was successful, false otherwise
    }

    /**
     * getGoogleMarketApi method
     *
     * @param marketApiId
     * @return
     */
    @SuppressLint("Range")
    public GoogleMarketApi getGoogleMarketApi(int marketApiId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                null,
                COLUMN_MARKET_API_ID + "=?",
                new String[]{String.valueOf(marketApiId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            GoogleMarketApi googleMarketApi = new GoogleMarketApi();
            googleMarketApi.setMarketApiId(cursor.getInt(cursor.getColumnIndex(COLUMN_MARKET_API_ID)));
            googleMarketApi.setApiUrl(cursor.getString(cursor.getColumnIndex(COLUMN_API_URL)));

            cursor.close();
            return googleMarketApi;
        } else {
            return null;
        }
    }

    /**
     * getAllGoogleMarketApis method
     *
     * @return
     */
    @SuppressLint("Range")
    public List<GoogleMarketApi> getAllGoogleMarketApis() {
        List<GoogleMarketApi> googleMarketApis = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                GoogleMarketApi googleMarketApi = new GoogleMarketApi();
                googleMarketApi.setMarketApiId(cursor.getInt(cursor.getColumnIndex(COLUMN_MARKET_API_ID)));
                googleMarketApi.setApiUrl(cursor.getString(cursor.getColumnIndex(COLUMN_API_URL)));

                googleMarketApis.add(googleMarketApi);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return googleMarketApis;
    }

    /**
     * updateGoogleMarketApi method
     *
     * @param googleMarketApi
     * @return
     */
    public int updateGoogleMarketApi(GoogleMarketApi googleMarketApi) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_API_URL, googleMarketApi.getApiUrl());

        return db.update(TABLE_NAME, values, COLUMN_MARKET_API_ID + " = ?",
                new String[]{String.valueOf(googleMarketApi.getMarketApiId())});
    }

    /**
     * deleteGoogleMarketApi method
     *
     * @param googleMarketApi
     * @return
     */
    public void deleteGoogleMarketApi(GoogleMarketApi googleMarketApi) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_MARKET_API_ID + " = ?",
                new String[]{String.valueOf(googleMarketApi.getMarketApiId())});
        db.close();
    }
}