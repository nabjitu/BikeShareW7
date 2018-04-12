package com.nataliebrammerjensen.bikesharemlk.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

/**
 * Created by nataliebrammerjensen on 14/03/2018.
 */

public class RideCursorWrapper extends CursorWrapper {

    //aka getCrime
    public RideCursorWrapper(Cursor cursor) {
    super(cursor);
    }

    public Ride getRide() {
        String uuidString = getString(getColumnIndex(RideDBSchema.RideTable.Cols.UUID));
        String bikeName = getString(getColumnIndex(RideDBSchema.RideTable.Cols.BIKENAME));
        String start = getString(getColumnIndex(RideDBSchema.RideTable.Cols.START));
        String stop = getString(getColumnIndex(RideDBSchema.RideTable.Cols.STOP));

        Ride ride = new Ride(""); //SQlProp id er tomt på det her tidspunkt.
        ride.setId(uuidString);
        ride.setMbikeName(bikeName);
        ride.setMstartRide(start);
        ride.setMendRide(stop);
        return ride;
    }
}
