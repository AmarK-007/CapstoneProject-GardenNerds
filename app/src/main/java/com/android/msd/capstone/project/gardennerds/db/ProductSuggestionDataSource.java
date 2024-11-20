package com.android.msd.capstone.project.gardennerds.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.msd.capstone.project.gardennerds.models.ProductSuggestion;

import java.util.ArrayList;
import java.util.List;

/**
 * DataSource class for ProductSuggestion
 */
public class ProductSuggestionDataSource {
    private DBHelper dbHelper;

    /**
     * Constructor for ProductSuggestionDataSource
     *
     * @param context
     * @return
     */
    public ProductSuggestionDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    // ProductSuggestion table name
    public static final String TABLE_NAME = "product_suggestions";
    public static final String COLUMN_SUGGESTION_ID = "suggestion_id";
    public static final String COLUMN_GARDEN_ID = "garden_id";
    public static final String COLUMN_MARKET_API_ID = "market_api_id";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_SUGGESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_GARDEN_ID + " INTEGER,"
                    + COLUMN_MARKET_API_ID + " INTEGER,"
                    + "FOREIGN KEY(" + COLUMN_GARDEN_ID + ") REFERENCES gardens(garden_id),"
                    + "FOREIGN KEY(" + COLUMN_MARKET_API_ID + ") REFERENCES market_api(market_api_id)"
                    + ")";

    /**
     * insertProductSuggestion method
     *
     * @param productSuggestion
     * @return
     */
    public boolean insertProductSuggestion(ProductSuggestion productSuggestion) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_GARDEN_ID, productSuggestion.getGardenId());
        values.put(COLUMN_MARKET_API_ID, productSuggestion.getMarketApiId());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result != -1; // Return true if insertion was successful, false otherwise
    }

    /**
     * getProductSuggestion method
     *
     * @param suggestionId
     * @return
     */
    @SuppressLint("Range")
    public ProductSuggestion getProductSuggestion(int suggestionId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                null,
                COLUMN_SUGGESTION_ID + "=?",
                new String[]{String.valueOf(suggestionId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            ProductSuggestion productSuggestion = new ProductSuggestion();
            productSuggestion.setSuggestionId(cursor.getInt(cursor.getColumnIndex(COLUMN_SUGGESTION_ID)));
            productSuggestion.setGardenId(cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID)));
            productSuggestion.setMarketApiId(cursor.getInt(cursor.getColumnIndex(COLUMN_MARKET_API_ID)));

            cursor.close();
            return productSuggestion;
        } else {
            return null;
        }
    }

    /**
     * getAllProductSuggestions method
     *
     * @return
     */
    @SuppressLint("Range")
    public List<ProductSuggestion> getAllProductSuggestions() {
        List<ProductSuggestion> productSuggestions = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProductSuggestion productSuggestion = new ProductSuggestion();
                productSuggestion.setSuggestionId(cursor.getInt(cursor.getColumnIndex(COLUMN_SUGGESTION_ID)));
                productSuggestion.setGardenId(cursor.getInt(cursor.getColumnIndex(COLUMN_GARDEN_ID)));
                productSuggestion.setMarketApiId(cursor.getInt(cursor.getColumnIndex(COLUMN_MARKET_API_ID)));

                productSuggestions.add(productSuggestion);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return productSuggestions;
    }

    /**
     * updateProductSuggestion method
     *
     * @param productSuggestion
     * @return
     */
    public int updateProductSuggestion(ProductSuggestion productSuggestion) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_GARDEN_ID, productSuggestion.getGardenId());
        values.put(COLUMN_MARKET_API_ID, productSuggestion.getMarketApiId());

        return db.update(TABLE_NAME, values, COLUMN_SUGGESTION_ID + " = ?",
                new String[]{String.valueOf(productSuggestion.getSuggestionId())});
    }

    /**
     * deleteProductSuggestion method
     *
     * @param productSuggestion
     * @return
     */
    public void deleteProductSuggestion(ProductSuggestion productSuggestion) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_SUGGESTION_ID + " = ?",
                new String[]{String.valueOf(productSuggestion.getSuggestionId())});
        db.close();
    }
}