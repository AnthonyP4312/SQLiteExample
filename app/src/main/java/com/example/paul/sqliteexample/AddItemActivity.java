package com.example.paul.sqliteexample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "addItemAct";
    EditText name;
    EditText city;
    Restaurant rest;
    Context appContext;
    RestDataSource rds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        appContext = this.getApplicationContext();

        rds = new RestDataSource(appContext);
        rds.open();
    }


    //Called when Submit button is pressed.
    //Adds new entry to Database and moves back to Restaurant List
    public void submitItem(View view) {
        Log.d(TAG, "Submitting new Restaurant: ");
        name = (EditText) findViewById(R.id.restName);
        city = (EditText) findViewById(R.id.restCity);
        Log.d(TAG, name.getText().toString());
        Log.d(TAG, city.getText().toString());

        //Add a restaurant entry using the names in the editText fields.
        rest = rds.createRest(name.getText().toString(), city.getText().toString());
        rds.close();

        //Return to Restaurant List after submitting
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
