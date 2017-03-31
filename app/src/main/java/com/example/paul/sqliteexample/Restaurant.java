package com.example.paul.sqliteexample;
import android.graphics.Bitmap;


public class Restaurant
{


    private long _id;
    private Bitmap Picture;
    private String Name;
    private String City;

    public Restaurant(long id, String name, String city){
        _id = id;
        Name = name;
        City = city;
    }

    public void setPicture(Bitmap picture) {
        Picture = picture;
    }

    public Bitmap getPicture() {
        return Picture;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        this._id = id;
    }

    public String toString(){
        return Name;
    }
}

