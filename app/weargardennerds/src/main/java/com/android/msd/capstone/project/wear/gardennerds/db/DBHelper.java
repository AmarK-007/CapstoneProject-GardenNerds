package com.android.msd.capstone.project.wear.gardennerds.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.msd.capstone.project.wear.gardennerds.utils.Constants;


/**
 * DBHelper class
 */
public class DBHelper extends SQLiteOpenHelper {

    private Context mContext;

    private static final String TAG = "debug_DBHelper";

    public static int getDatabaseVersion() {
        return Constants.DATABASE_VERSION;
    }

    /**
     * Constructor for DBHelper
     *
     * @param context Constructor should be private to prevent direct instantiation.
     *                Make a call to the static method "getInstance()" instead.
     */
    private DBHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        mContext = context.getApplicationContext();
        Log.v(TAG, "Inside constructor");
    }

    public static synchronized DBHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (context != null) {
            return new DBHelper(context.getApplicationContext());
        }
        return null;
    }

    /**
     * onCreate method
     *
     * @param db
     * @return
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.v(TAG, "Inside onCreate");
            db.execSQL(GardenDataSource.CREATE_TABLE);
            db.execSQL(PlantDataSource.CREATE_TABLE);
            db.execSQL(ReminderDataSource.CREATE_TABLE);
            db.execSQL(SoilSensorDataSource.CREATE_TABLE);
            db.execSQL(UserDataSource.CREATE_TABLE);
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: " + e.getMessage());
        }
    }

    /**
     * onUpgrade method
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     * @return
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Log.v(TAG, "Inside onUpgrade");
        /*
          For now drop table and create new one in case of upgrade
          In future, we can implement migration logic here  with alter table and retain data.
          */
            db.execSQL("DROP TABLE IF EXISTS " + GardenDataSource.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + PlantDataSource.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + ReminderDataSource.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + SoilSensorDataSource.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + UserDataSource.TABLE_NAME);
            onCreate(db);
        } catch (Exception e) {
            Log.e(TAG, "Error in onUpgrade: " + e.getMessage());
        }

    }

    /**
     * closeCursor method
     *
     * @param cursor
     */
    public static void closeCursor(Cursor cursor) {
        try {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }
}
