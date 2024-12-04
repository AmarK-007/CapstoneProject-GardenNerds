package com.android.msd.capstone.project.wear.gardennerds.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.android.msd.capstone.project.wear.gardennerds.models.Reminder;

import java.util.ArrayList;
import java.util.List;

/**
 * DataSource class for Reminder
 */
public class ReminderDataSource {
    private DBHelper dbHelper;

    /**
     * Constructor for ReminderDataSource
     *
     * @param context
     * @return
     */
    public ReminderDataSource(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    // Reminder table name
    public static final String TABLE_NAME = "reminders";
    public static final String COLUMN_REMINDER_ID = "reminder_id";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_DATE_TIME = "date_time";
    public static final String COLUMN_PLANT_ID = "plant_id";
    public static final String COLUMN_REMINDER_TYPE_ID = "reminder_type_id";
    public static final String COLUMN_FREQUENCY = "frequency";
    public static final String COLUMN_MOISTURE_LEVEL = "moisture_level";
    public static final String COLUMN_TEMPERATURE_LEVEL = "temperature_level";
    public static final String COLUMN_SUNLIGHT_LEVEL = "sunlight_level";
    public static final String COLUMN_NUTRIENT_REQUIRED = "nutrient_required";
    public static final String COLUMN_REMINDER_TIME = "reminder_time";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_REMINDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_MESSAGE + " TEXT,"
                    + COLUMN_DATE_TIME + " TIMESTAMP,"
                    + COLUMN_PLANT_ID + " INTEGER,"
                    + COLUMN_REMINDER_TYPE_ID + " INTEGER,"
                    + COLUMN_FREQUENCY + " TEXT,"
                    + COLUMN_MOISTURE_LEVEL + " TEXT,"
                    + COLUMN_TEMPERATURE_LEVEL + " TEXT,"
                    + COLUMN_SUNLIGHT_LEVEL + " TEXT,"
                    + COLUMN_NUTRIENT_REQUIRED + " TEXT,"
                    + COLUMN_REMINDER_TIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                    + "FOREIGN KEY(" + COLUMN_PLANT_ID + ") REFERENCES plants(plant_id),"
                    + "FOREIGN KEY(" + COLUMN_REMINDER_TYPE_ID + ") REFERENCES reminder_types(reminder_type_id)"
                    + ")";

    /**
     * insertReminder method
     *
     * @param reminder
     * @return
     */
    public long insertReminder(Reminder reminder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGE, reminder.getMessage());
        values.put(COLUMN_DATE_TIME, reminder.getDateTime());
        values.put(COLUMN_PLANT_ID, reminder.getPlantId());
        values.put(COLUMN_REMINDER_TYPE_ID, reminder.getReminderTypeId());
        values.put(COLUMN_FREQUENCY, reminder.getFrequency());
        values.put(COLUMN_MOISTURE_LEVEL, reminder.getMoistureLevel());
        values.put(COLUMN_TEMPERATURE_LEVEL, reminder.getTemperatureLevel());
        values.put(COLUMN_SUNLIGHT_LEVEL, reminder.getSunlightLevel());
        values.put(COLUMN_NUTRIENT_REQUIRED, reminder.getNutrientRequired());
        values.put(COLUMN_REMINDER_TIME, reminder.getReminderTime());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();

        return result;
    }

    /**
     * getReminder method
     *
     * @param reminderId
     * @return
     */
    @SuppressLint("Range")
    public Reminder getReminder(int reminderId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                null,
                COLUMN_REMINDER_ID + "=?",
                new String[]{String.valueOf(reminderId)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Reminder reminder = new Reminder();
            reminder.setReminderId(cursor.getInt(cursor.getColumnIndex(COLUMN_REMINDER_ID)));
            reminder.setMessage(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE)));
            reminder.setDateTime(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME)));
            reminder.setPlantId(cursor.getInt(cursor.getColumnIndex(COLUMN_PLANT_ID)));
            reminder.setReminderTypeId(cursor.getInt(cursor.getColumnIndex(COLUMN_REMINDER_TYPE_ID)));
            reminder.setFrequency(cursor.getString(cursor.getColumnIndex(COLUMN_FREQUENCY)));
            reminder.setMoistureLevel(cursor.getString(cursor.getColumnIndex(COLUMN_MOISTURE_LEVEL)));
            reminder.setTemperatureLevel(cursor.getString(cursor.getColumnIndex(COLUMN_TEMPERATURE_LEVEL)));
            reminder.setSunlightLevel(cursor.getString(cursor.getColumnIndex(COLUMN_SUNLIGHT_LEVEL)));
            reminder.setNutrientRequired(cursor.getString(cursor.getColumnIndex(COLUMN_NUTRIENT_REQUIRED)));
            reminder.setReminderTime(cursor.getString(cursor.getColumnIndex(COLUMN_REMINDER_TIME)));

            cursor.close();
            return reminder;
        } else {
            return null;
        }
    }

    /**
     * getAllReminders method
     *
     * @return
     */
    @SuppressLint("Range")
    public List<Reminder> getAllReminders() {
        List<Reminder> reminders = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();
                reminder.setReminderId(cursor.getInt(cursor.getColumnIndex(COLUMN_REMINDER_ID)));
                reminder.setMessage(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE)));
                reminder.setDateTime(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME)));
                reminder.setPlantId(cursor.getInt(cursor.getColumnIndex(COLUMN_PLANT_ID)));
                reminder.setReminderTypeId(cursor.getInt(cursor.getColumnIndex(COLUMN_REMINDER_TYPE_ID)));
                reminder.setFrequency(cursor.getString(cursor.getColumnIndex(COLUMN_FREQUENCY)));
                reminder.setMoistureLevel(cursor.getString(cursor.getColumnIndex(COLUMN_MOISTURE_LEVEL)));
                reminder.setTemperatureLevel(cursor.getString(cursor.getColumnIndex(COLUMN_TEMPERATURE_LEVEL)));
                reminder.setSunlightLevel(cursor.getString(cursor.getColumnIndex(COLUMN_SUNLIGHT_LEVEL)));
                reminder.setNutrientRequired(cursor.getString(cursor.getColumnIndex(COLUMN_NUTRIENT_REQUIRED)));
                reminder.setReminderTime(cursor.getString(cursor.getColumnIndex(COLUMN_REMINDER_TIME)));

                reminders.add(reminder);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return reminders;
    }

    /**
     * getAllReminders by plantId method
     *
     * @return
     */
    @SuppressLint("Range")
    public List<Reminder> getRemindersByPlantId(int plantId) {
        List<Reminder> reminders = new ArrayList<>();

        // Modify the query to filter by plantId
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_PLANT_ID + " = ?";

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(plantId)}); // Use plantId as the parameter

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();
                reminder.setReminderId(cursor.getInt(cursor.getColumnIndex(COLUMN_REMINDER_ID)));
                reminder.setMessage(cursor.getString(cursor.getColumnIndex(COLUMN_MESSAGE)));
                reminder.setDateTime(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME)));
                reminder.setPlantId(cursor.getInt(cursor.getColumnIndex(COLUMN_PLANT_ID)));
                reminder.setReminderTypeId(cursor.getInt(cursor.getColumnIndex(COLUMN_REMINDER_TYPE_ID)));
                reminder.setFrequency(cursor.getString(cursor.getColumnIndex(COLUMN_FREQUENCY)));
                reminder.setMoistureLevel(cursor.getString(cursor.getColumnIndex(COLUMN_MOISTURE_LEVEL)));
                reminder.setTemperatureLevel(cursor.getString(cursor.getColumnIndex(COLUMN_TEMPERATURE_LEVEL)));
                reminder.setSunlightLevel(cursor.getString(cursor.getColumnIndex(COLUMN_SUNLIGHT_LEVEL)));
                reminder.setNutrientRequired(cursor.getString(cursor.getColumnIndex(COLUMN_NUTRIENT_REQUIRED)));
                reminder.setReminderTime(cursor.getString(cursor.getColumnIndex(COLUMN_REMINDER_TIME)));

                reminders.add(reminder);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return reminders;
    }

    /**
     * updateReminder method
     *
     * @param reminder
     * @return
     */
    public int updateReminder(Reminder reminder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGE, reminder.getMessage());
        values.put(COLUMN_DATE_TIME, reminder.getDateTime());
        values.put(COLUMN_PLANT_ID, reminder.getPlantId());
        values.put(COLUMN_REMINDER_TYPE_ID, reminder.getReminderTypeId());
        values.put(COLUMN_FREQUENCY, reminder.getFrequency());
        values.put(COLUMN_MOISTURE_LEVEL, reminder.getMoistureLevel());
        values.put(COLUMN_TEMPERATURE_LEVEL, reminder.getTemperatureLevel());
        values.put(COLUMN_SUNLIGHT_LEVEL, reminder.getSunlightLevel());
        values.put(COLUMN_NUTRIENT_REQUIRED, reminder.getNutrientRequired());
        values.put(COLUMN_REMINDER_TIME, reminder.getReminderTime());

        return db.update(TABLE_NAME, values, COLUMN_REMINDER_ID + " = ?",
                new String[]{String.valueOf(reminder.getReminderId())});
    }

    /**
     * deleteReminder method
     *
     * @param reminder
     * @return
     */
    public void deleteReminder(Reminder reminder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_REMINDER_ID + " = ?",
                new String[]{String.valueOf(reminder.getReminderId())});
        db.close();
    }

    public void deleteRemindersByPlantId(int plantId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_PLANT_ID + " = ?", new String[]{String.valueOf(plantId)});
        db.close();
    }
}