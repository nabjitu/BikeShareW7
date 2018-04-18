package com.nataliebrammerjensen.bikesharemlk.database;

import io.realm.RealmObject;

/**
 * Created by nataliebrammerjensen on 13/04/2018.
 */

public class Bike extends RealmObject{
    private int mId;
    private String mName;
    private String mOwner;
    private String mBrand;
    private boolean mAvailable;
    private double mHourPrice;

    public Bike(int id, String name, String owner, String brand, boolean available, double price){
        mId = id;
        mName = name;
        mOwner = owner;
        mBrand = brand;
        mAvailable = available;
        mHourPrice = price;
    }

    public Bike(){
        mId = 0;
        mName = null;
        mOwner = null;
        mBrand = null;
        mAvailable = false;
        mHourPrice = 0;
    }

    public boolean isAvailable() {
        return mAvailable;
    }

    public double getHourPrice() {
        return mHourPrice;
    }

    public void setAvailable(boolean available) {
        mAvailable = available;
    }

    public void setHourPrice(double hourPrice) {
        mHourPrice = hourPrice;
    }

    public void setBrand(String brand) {
        mBrand = brand;
    }

    public void setAvailable(String available) {
        mBrand = available;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    public int getId() {
        return mId;
    }

    public String getBrand() {
        return mBrand;
    }

    public boolean getAvailable() {
        return mAvailable;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public String getOwner() {
        return mOwner;
    }

    public String getPhotoFilename() {
        return "IMG_" + getId() + ".jpg";
    }


}
