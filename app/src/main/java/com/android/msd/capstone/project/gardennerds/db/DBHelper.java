package com.android.msd.capstone.project.gardennerds.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.msd.capstone.project.gardennerds.utils.Constants;


/**
 * DBHelper class
 */
public class DBHelper extends SQLiteOpenHelper {
    /**
     * Constructor for DBHelper
     *
     * @param context
     * @return
     */
    public DBHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    /**
     * onCreate method
     *
     * @param db
     * @return
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDataSource.CREATE_TABLE);
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
        db.execSQL("DROP TABLE IF EXISTS " + UserDataSource.TABLE_NAME);
        onCreate(db);
    }
}
