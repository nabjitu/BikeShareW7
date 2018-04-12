package com.nataliebrammerjensen.bikesharemlk.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.UUID;

/**
 * Created by nataliebrammerjensen on 10/04/2018.
 */

public class RidesDB  extends Observable {   // Singleton
    private static RidesDB sRidesDB;
    private static SQLiteDatabase mDatabase;

    public static RidesDB get(Context context) {
        if (sRidesDB == null) {
            sRidesDB= new RidesDB(context);
        }
        return sRidesDB;
    }

    private RidesDB(Context context) {
        mallRides= new ArrayList<>();

        context = context.getApplicationContext();
        mDatabase = new RideBaseHelper(context).getWritableDatabase();

        // Add some rides for testing purposes
        addRide(new Ride("Peters bike", "ITU", "Fields", ""));
        addRide(new Ride("Peters bike", "Fields", "Kongens Nytorv", ""));
        addRide(new Ride("Jørgens bike", "Home", "ITU", ""));
    }

    private List<Ride> mallRides;

    public List<Ride> getRidesDB() { return mallRides; }

    public void delete(Ride r, Context c) {
        mallRides.remove(r);
        Toast.makeText(c, "Deleted ride " + r.getMbikeName(), Toast.LENGTH_LONG).show();
        this.setChanged();
        notifyObservers();
    }

    private static ContentValues getContentValues(Ride ride) {
        ContentValues values = new ContentValues();
        //values.put(RideDBSchema.RideTable.Cols.UUID, ride.getId());
        values.put(RideDBSchema.RideTable.Cols.BIKENAME, ride.getMbikeName());
        values.put(RideDBSchema.RideTable.Cols.START, ride.getMstartRide());
        values.put(RideDBSchema.RideTable.Cols.STOP, ride.getMendRide());
        values.put(RideDBSchema.RideTable.Cols.DATE, ride.getmStartddmmyyyy());
        values.put(RideDBSchema.RideTable.Cols.TIME, ride.getmStarthhmmss());
        return values;
    }

    public void addRide(Ride r) {
        ContentValues values = getContentValues(r);
        mDatabase.insert(RideDBSchema.RideTable.NAME, null, values);
    }

    public void replaceLast(Ride substitute, String old){
        //HFM19.30.1
        //Prepared statement to avid sql injection
        mDatabase.update(RideDBSchema.RideTable.NAME, getContentValues(substitute), "uuid =?", new String[]{old} );
    }

    /*public void replaceLastTwo(Activity activity){
        getlastAdded(activity).ge
        mDatabase.update(RideDBSchema.RideTable.NAME, getContentValues(substitute), "uuid =?", new String[]{old} );
    }*/

    public RideCursorWrapper queryRides(String whereClause, String[] whereArgs, String orderBy, String limit) {
        Cursor cursor = mDatabase.query(RideDBSchema.RideTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                orderBy,  // orderBy
                limit
        );
        return new RideCursorWrapper(cursor);
    }

    public ArrayList<Ride> getAllRides() {
        ArrayList<Ride> rides = new ArrayList<>();
        RideCursorWrapper cursor = queryRides(null, null, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                rides.add(cursor.getRide());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return rides;
    }

    public Ride getRide(UUID id) {
        RideCursorWrapper cursor = queryRides(

                RideDBSchema.RideTable.Cols.UUID + " = ?",
                new String[] { id.toString() }, null, null
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getRide();
        } finally {
            cursor.close();
        }
    }

    public Ride getlastAdded(Activity activity){
        Context context = activity.getApplicationContext();
        RidesDB rdb = RidesDB.get(context);
        String orderBy = " uuid DESC ";
        String limit = "1";
        RideCursorWrapper cursor = rdb.queryRides(null, null, orderBy, limit);
        if(cursor.getCount() >= 1){
            while(cursor.moveToNext()){
                Ride newRide = cursor.getRide();
                return newRide;
            }
        }
        System.out.print("cursosr is 0");
        return  null;
    }

    /*public String getId (Ride r, Activity activity){
        Context context = activity.getApplicationContext();
        RidesDB rdb = RidesDB.get(context);RideCursorWrapper cursor = rdb.queryRides("uuid =?", new String[]{r.getId()} , null, null);
    }*/

    public String getIdOnlastAdded(Activity activity){

        Context context = activity.getApplicationContext();
        RidesDB rdb = RidesDB.get(context);
        String orderBy = " uuid DESC ";
        String limit = "1";
        RideCursorWrapper cursor = rdb.queryRides(null, null, orderBy, limit);
        if(cursor.getCount() >= 1){
            while(cursor.moveToNext()){
                Ride newRide = cursor.getRide();
                return newRide.getId();
            }
        }
        System.out.print("cursor is 0");
        return  null;
    }

    public void updateLastRide(String oldId, String endValue) {
        String tableName = RideDBSchema.RideTable.NAME;
        updateOneColumn(tableName, "stop", oldId, "uuid", endValue);
    }

    public void updateOneColumn(String TABLE_NAME, String Column, String rowId, String ColumnName, String newValue){
        System.out.println("uuid is: " + rowId);
        String sql = "UPDATE "+TABLE_NAME +" SET " + ColumnName+ " = " + rowId + " WHERE "+Column+ " = "+rowId; //Her driller det.
        mDatabase.beginTransaction();
        SQLiteStatement stmt = mDatabase.compileStatement(sql);
        try{
            stmt.execute();
            mDatabase.setTransactionSuccessful();
        }finally{
            mDatabase.endTransaction();
        }

    }

}
