package com.nataliebrammerjensen.bikesharemlk.database;

import java.text.SimpleDateFormat;
import java.util.UUID;

import io.realm.RealmObject;

/**
 * Created by nataliebrammerjensen on 10/04/2018.
 */

public class Ride extends RealmObject {
    private String mbikeName;
    private String mstartRide;
    private String mendRide;
    private String mStartddmmyyyy;
    private String mStarthhmmss;
    private int mId;

    public Ride(int id) { // mainly for testing
        mbikeName= null;
        mstartRide= null;
        mId = id;

        mendRide= null;
        mStartddmmyyyy = new SimpleDateFormat("dd.MM.yyyy").format(new java.util.Date());
        mStarthhmmss = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
    }

    public Ride(){
        mbikeName= null;
        mstartRide= null;
        mId = 0;

        mendRide= null;
        mStartddmmyyyy = new SimpleDateFormat("dd.MM.yyyy").format(new java.util.Date());
        mStarthhmmss = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
    }

    public Ride(String name, String startRide, String endRide, int id) { // mainly for testing
        mbikeName= name;
        mstartRide= startRide;
        mId = id;
        mendRide= endRide;
        mStartddmmyyyy = new SimpleDateFormat("dd.MM.yyyy").format(new java.util.Date());
        mStarthhmmss = new SimpleDateFormat("HH:mm:ss").format(new java.util.Date());
    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public String getMbikeName() {
        return mbikeName;
    }

    public void setMbikeName(String mbikeName) {
        this.mbikeName = mbikeName;
    }

    public String getMstartRide() {
        return mstartRide;
    }

    public void setMstartRide(String mstartRide) {
        this.mstartRide = mstartRide;
    }

    public String getMendRide() { return mendRide; }

    public void setMendRide(String mendRide) { this.mendRide = mendRide; }

    public String toString(String delim) { return mbikeName + delim + mstartRide ; }

    public String toString() { return mbikeName + " started: " + mstartRide + " ended: " + mendRide; }

    public String getmStartddmmyyyy() {
        return mStartddmmyyyy;
    }

    public String getmStarthhmmss() {
        return mStarthhmmss;
    }
}
