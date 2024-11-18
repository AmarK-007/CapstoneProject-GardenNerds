package com.android.msd.capstone.project.gardennerds.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.msd.capstone.project.gardennerds.models.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * DataSource class for Order
 */
public class OrderDataSource {
    private DBHelper dbHelper;

    /**
     * Constructor for OrderDataSource
     *
     * @param context
     * @return
     */
    public OrderDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Order table name
    public static final String TABLE_NAME = "orders";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_PURCHASE_DATE = "purchase_date";
    public static final String COLUMN_PAYMENT_TYPE = "payment_type";
    public static final String COLUMN_DELIVERY_STATUS = "delivery_status";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_MARKET_API_ID = "market_api_id";
    public static final String COLUMN_SUGGESTION_ID = "suggestion_id";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_USER_ID + " INTEGER,"
                    + COLUMN_PURCHASE_DATE + " TEXT,"
                    + COLUMN_PAYMENT_TYPE + " TEXT,"
                    + COLUMN_DELIVERY_STATUS + " TEXT,"
                    + COLUMN_AMOUNT + " FLOAT,"
                    + COLUMN_MARKET_API_ID + " INTEGER,"
                    + COLUMN_SUGGESTION_ID + " INTEGER,"
                    + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES users(user_id),"
                    + "FOREIGN KEY(" + COLUMN_MARKET_API_ID + ") REFERENCES market_api(market_api_id),"
                    + "FOREIGN KEY(" + COLUMN_SUGGESTION_ID + ") REFERENCES suggestions(suggestion_id)"
                    + ")";

    /**
     * insertOrder method
     *
     * @param order
     * @return
     */
    public boolean insertOrder(Order order) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, order.getUserId());
        values.put(COLUMN_PURCHASE_DATE, order.getPurchaseDate());
        values.put(COLUMN_PAYMENT_TYPE, order.getPaymentType());
        values.put(COLUMN_DELIVERY_STATUS, order.getDeliveryStatus());
        values.put(COLUMN_AMOUNT, order.getAmount());
        values.put(COLUMN_MARKET_API_ID, order.getMarketApiId());
        values.put(COLUMN_SUGGESTION_ID, order.getSuggestionId());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result != -1; // Return true if insertion was successful, false otherwise
    }

    /**
     * getOrder method
     *
     * @param orderId
     * @return
     */
    @SuppressLint("Range")
    public Order getOrder(int orderId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                null,
                COLUMN_ORDER_ID + "=?",
                new String[]{String.valueOf(orderId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Order order = new Order();
            order.setOrderId(cursor.getInt(cursor.getColumnIndex(COLUMN_ORDER_ID)));
            order.setUserId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
            order.setPurchaseDate(cursor.getString(cursor.getColumnIndex(COLUMN_PURCHASE_DATE)));
            order.setPaymentType(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_TYPE)));
            order.setDeliveryStatus(cursor.getString(cursor.getColumnIndex(COLUMN_DELIVERY_STATUS)));
            order.setAmount(cursor.getFloat(cursor.getColumnIndex(COLUMN_AMOUNT)));
            order.setMarketApiId(cursor.getInt(cursor.getColumnIndex(COLUMN_MARKET_API_ID)));
            order.setSuggestionId(cursor.getInt(cursor.getColumnIndex(COLUMN_SUGGESTION_ID)));

            cursor.close();
            return order;
        } else {
            return null;
        }
    }

    /**
     * getAllOrders method
     *
     * @return
     */
    @SuppressLint("Range")
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setOrderId(cursor.getInt(cursor.getColumnIndex(COLUMN_ORDER_ID)));
                order.setUserId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                order.setPurchaseDate(cursor.getString(cursor.getColumnIndex(COLUMN_PURCHASE_DATE)));
                order.setPaymentType(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_TYPE)));
                order.setDeliveryStatus(cursor.getString(cursor.getColumnIndex(COLUMN_DELIVERY_STATUS)));
                order.setAmount(cursor.getFloat(cursor.getColumnIndex(COLUMN_AMOUNT)));
                order.setMarketApiId(cursor.getInt(cursor.getColumnIndex(COLUMN_MARKET_API_ID)));
                order.setSuggestionId(cursor.getInt(cursor.getColumnIndex(COLUMN_SUGGESTION_ID)));

                orders.add(order);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return orders;
    }

    /**
     * updateOrder method
     *
     * @param order
     * @return
     */
    public int updateOrder(Order order) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, order.getUserId());
        values.put(COLUMN_PURCHASE_DATE, order.getPurchaseDate());
        values.put(COLUMN_PAYMENT_TYPE, order.getPaymentType());
        values.put(COLUMN_DELIVERY_STATUS, order.getDeliveryStatus());
        values.put(COLUMN_AMOUNT, order.getAmount());
        values.put(COLUMN_MARKET_API_ID, order.getMarketApiId());
        values.put(COLUMN_SUGGESTION_ID, order.getSuggestionId());

        return db.update(TABLE_NAME, values, COLUMN_ORDER_ID + " = ?",
                new String[]{String.valueOf(order.getOrderId())});
    }

    /**
     * deleteOrder method
     *
     * @param order
     * @return
     */
    public void deleteOrder(Order order) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ORDER_ID + " = ?",
                new String[]{String.valueOf(order.getOrderId())});
        db.close();
    }
}