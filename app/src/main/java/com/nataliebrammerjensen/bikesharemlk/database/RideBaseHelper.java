package com.nataliebrammerjensen.bikesharemlk.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nataliebrammerjensen on 14/03/2018.
 */

public class RideBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public RideBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + RideDBSchema.RideTable.NAME +
                "(" + RideDBSchema.RideTable.Cols.UUID + " integer primary key autoincrement, "
                + RideDBSchema.RideTable.Cols.BIKENAME + ", "
                + RideDBSchema.RideTable.Cols.START + ", "
                + RideDBSchema.RideTable.Cols.STOP  + ", "
                + RideDBSchema.RideTable.Cols.DATE + ", "
                + RideDBSchema.RideTable.Cols.TIME +
                ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
