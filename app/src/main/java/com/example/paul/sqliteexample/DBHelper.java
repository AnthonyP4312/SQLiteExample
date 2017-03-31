package com.example.paul.sqliteexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This is a helper class with will Create and upgrade the Database
 */

public class DBHelper extends SQLiteOpenHelper {

    //Strings which declare names of the tables/Columns
    public static final String TABLE_REST = "restaurants";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_REST = "restaurant";
    public static final String COLUMN_CITY = "city";

    //Strings of the Database Name/Version
    public static final String DB_NAME = "restaurants.db";
    public static final int DB_VERSION = 2;

    //SQL String to create the Database
    public static final String DB_CREATE = "create table "
            + TABLE_REST + "( " + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_REST + " text not null, "
            + COLUMN_CITY + " text not null"
            + ");";

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }


    //This method is called on Startup if the DB Version number has changed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REST);
        onCreate(db);
    }
}
