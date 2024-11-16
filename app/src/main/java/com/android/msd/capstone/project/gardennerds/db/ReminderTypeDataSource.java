package com.android.msd.capstone.project.gardennerds.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.msd.capstone.project.gardennerds.models.ReminderType;

import java.util.ArrayList;
import java.util.List;

/**
 * DataSource class for ReminderType
 */
public class ReminderTypeDataSource {
    private DBHelper dbHelper;

    /**
     * Constructor for ReminderTypeDataSource
     *
     * @param context
     * @return
     */
    public ReminderTypeDataSource(Context context) {
        dbHelper = new DBHelper(context);
    }

    // ReminderType table name
    public static final String TABLE_NAME = "reminder_types";
    public static final String COLUMN_REMINDER_TYPE_ID = "reminder_type_id";
    public static final String COLUMN_REMINDER_TYPE_NAME = "reminder_type_name";
    public static final String COLUMN_DESCRIPTION = "description";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_REMINDER_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_REMINDER_TYPE_NAME + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT"
                    + ")";

    /**
     * insertReminderType method
     *
     * @param reminderType
     * @return
     */
    public boolean insertReminderType(ReminderType reminderType) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_REMINDER_TYPE_NAME, reminderType.getReminderTypeName());
        values.put(COLUMN_DESCRIPTION, reminderType.getDescription());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result != -1; // Return true if insertion was successful, false otherwise
    }

    /**
     * getReminderType method
     *
     * @param reminderTypeId
     * @return
     */
    @SuppressLint("Range")
    public ReminderType getReminderType(int reminderTypeId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                null,
                COLUMN_REMINDER_TYPE_ID + "=?",
                new String[]{String.valueOf(reminderTypeId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            ReminderType reminderType = new ReminderType();
            reminderType.setReminderTypeId(cursor.getInt(cursor.getColumnIndex(COLUMN_REMINDER_TYPE_ID)));
            reminderType.setReminderTypeName(cursor.getString(cursor.getColumnIndex(COLUMN_REMINDER_TYPE_NAME)));
            reminderType.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));

            cursor.close();
            return reminderType;
        } else {
            return null;
        }
    }

    /**
     * getAllReminderTypes method
     *
     * @return
     */
    @SuppressLint("Range")
    public List<ReminderType> getAllReminderTypes() {
        List<ReminderType> reminderTypes = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ReminderType reminderType = new ReminderType();
                reminderType.setReminderTypeId(cursor.getInt(cursor.getColumnIndex(COLUMN_REMINDER_TYPE_ID)));
                reminderType.setReminderTypeName(cursor.getString(cursor.getColumnIndex(COLUMN_REMINDER_TYPE_NAME)));
                reminderType.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));

                reminderTypes.add(reminderType);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return reminderTypes;
    }

    /**
     * updateReminderType method
     *
     * @param reminderType
     * @return
     */
    public int updateReminderType(ReminderType reminderType) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_REMINDER_TYPE_NAME, reminderType.getReminderTypeName());
        values.put(COLUMN_DESCRIPTION, reminderType.getDescription());

        return db.update(TABLE_NAME, values, COLUMN_REMINDER_TYPE_ID + " = ?",
                new String[]{String.valueOf(reminderType.getReminderTypeId())});
    }

    /**
     * deleteReminderType method
     *
     * @param reminderType
     * @return
     */
    public void deleteReminderType(ReminderType reminderType) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_REMINDER_TYPE_ID + " = ?",
                new String[]{String.valueOf(reminderType.getReminderTypeId())});
        db.close();
    }
}