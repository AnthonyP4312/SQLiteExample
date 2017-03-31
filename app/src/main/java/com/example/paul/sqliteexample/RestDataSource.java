package com.example.paul.sqliteexample;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.Tag;
import android.util.Log;
import static android.content.ContentValues.TAG;

/**
 * This is a class which handles Data going in and out of the database
 */

public class RestDataSource {

    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private String[] allColumns = {DBHelper.COLUMN_ID, DBHelper.COLUMN_REST, DBHelper.COLUMN_CITY};

    public RestDataSource(Context context){
        dbHelper = new DBHelper(context);
    }

    //Open access to Database
    public void open() throws SQLException{
        db = dbHelper.getWritableDatabase();
    }

    //Close access to database
    public void close(){
        dbHelper.close();
    }

    //Creates a restaurant entry in the Database and returns the entry as a restaurant Object
    public Restaurant createRest(String restaurant, String city){

        ContentValues values = new ContentValues();     //creates a list of Key-Value pairs to add
        values.put(DBHelper.COLUMN_REST, restaurant);   //Adds a restaurant name under the restaurants column
        values.put(DBHelper.COLUMN_CITY, city);         //Adds a city name under the city column
        long insertId = db.insert(DBHelper.TABLE_REST, null,    //inserts new row and returns unique ID
                values);
        Cursor cursor = db.query(DBHelper.TABLE_REST,
                allColumns, DBHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();   //points cursor to newest row
        Restaurant newRest = cursorToRest(cursor);  //Returns newest row
        cursor.close();
        return newRest;
    }

    //Delete a restaurant Entry in the database
    public void deleteRest(Restaurant restaurant){
        long id = restaurant.getId();
        Log.d(TAG, "Deleting id: " + id);
        db.delete(DBHelper.TABLE_REST, DBHelper.COLUMN_ID + " = " + id, null);
    }

    //returns a list of all Restaurants in the database
    public List<Restaurant> getAllRestaurants(){
        List<Restaurant> restaurants = new ArrayList<Restaurant>();

        //creates a Cursor pointing to the Restaurant Table
        Cursor cursor = db.query(DBHelper.TABLE_REST, allColumns, null, null, null, null, null);

        //Iterates through entire table and adds each restaurant to List
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Restaurant restaurant = cursorToRest(cursor);
            restaurants.add(restaurant);
            cursor.moveToNext();
        }

        cursor.close();
        return restaurants;

    }

    //Returns the row currently being pointed to as a Restaurant Object
    private Restaurant cursorToRest(Cursor cursor){
        Restaurant rest = new Restaurant(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
        return rest;
    }
}
