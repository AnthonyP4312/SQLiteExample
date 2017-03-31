package com.example.paul.sqliteexample;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main";
    ListView restList;
    ArrayAdapter adapter;
    Context appContext;
    private RestDataSource restDataSource;

    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);






        restList = (ListView) findViewById(R.id.list);
        appContext = this.getApplicationContext();

        restDataSource = new RestDataSource(appContext);
        restDataSource.open();

        List<Restaurant> values = restDataSource.getAllRestaurants();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, values);
        restList.setAdapter(adapter);

        restList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView parent, View v, int position, long id){

                Log.d(TAG, "short Clicked" + Long.toString(id));


            }
        });

        restList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView parent, View v, int position, final long id){


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Delete Entry?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "User clicked delete");
                        if (restList.getCount() > 0) {
                            Restaurant rest = (Restaurant) adapter.getItem((int) id);
                            restDataSource.deleteRest(rest);
                            adapter.remove(rest);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "User clicked Cancel");
                        //do nothing
                    }
                });
                dialog = builder.create();

                dialog.show();

                Log.d(TAG, "Long Clicked");
                return true;
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        restDataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        restDataSource.close();
        super.onPause();
    }

    public void onClick(View view){
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);

//        Log.d(TAG, "button press");
//        Restaurant rest = null;
//        String[] restaurants = new String[] {
//                "McDonalds",
//                "Marinas",
//                "Starbucks"
//        };
//
//        String city = "League City";
//
//        int nextInt = new Random().nextInt(3);
//        rest = restDataSource.createRest(restaurants[nextInt], city);
//        adapter.add(rest);
//
//        adapter.notifyDataSetChanged();

    }



}
