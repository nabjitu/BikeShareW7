package com.nataliebrammerjensen.bikesharemlk;

import android.util.Log;

import com.nataliebrammerjensen.bikesharemlk.database.Ride;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by nataliebrammerjensen on 12/04/2018.
 */

public class RealmDBStuff {

    public RealmDBStuff(){

    }

    public static void showDataWithWhereClause(Realm realm, String uuid) {
        RealmResults<Ride> rides = realm.where(Ride.class).equalTo("uuid", uuid).findAll();
        realm.beginTransaction();
        for (Ride ride : rides) {
            System.out.println(ride.getMbikeName());
        }
        realm.commitTransaction();
    }

    public static void showDataWithoutWhereClause(Realm realm) {
        RealmResults<Ride> rides = realm.where(Ride.class).findAll();
        realm.beginTransaction();
        for (Ride ride : rides) {
            System.out.println(ride.getMbikeName());
        }
        realm.commitTransaction();
    }

    public static ArrayList<Ride> getDataWithoutWhereClause(Realm realm) {
        RealmResults<Ride> rides = realm.where(Ride.class).findAll();
        ArrayList<Ride> result = new ArrayList<>();
        realm.beginTransaction();
        for (Ride ride : rides) {
            result.add(ride);
        }
        realm.commitTransaction();
        return result;
    }

    public static void changeData(Realm realm, String uuid, String bikeEnd){
        RealmResults<Ride> rides = realm.where(Ride.class).equalTo("uuid", uuid).findAll();

        realm.beginTransaction();
        for (Ride ride : rides) {
            ride.setMendRide(bikeEnd);
        }
        realm.commitTransaction();
    }

    public static void writeToDB(Realm realm, final String bikeName, final String startRide){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Ride ride = bgRealm.createObject(Ride.class);
                ride.setMbikeName(bikeName);
                ride.setMstartRide(startRide);
            }
        },  new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                //Transaction was a success.
                Log.v("Database", "Data Inserted");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                //TRansaction failed and was automaticcaly cancelled
                Log.e("Database", error.getMessage());

            }
        });
    }

    public static void writeRideToDB(Realm realm, final Ride rideIn){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Ride ride = bgRealm.createObject(Ride.class);
                ride.setMbikeName(rideIn.getMbikeName());
                ride.setMstartRide(rideIn.getMstartRide());
                ride.setMstartRide(rideIn.getmStartddmmyyyy());
                ride.setMstartRide(rideIn.getmStarthhmmss());
            }
        },  new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                //Transaction was a success.
                Log.v("Database", "Data Inserted");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                //TRansaction failed and was automaticcaly cancelled
                Log.e("Database", error.getMessage());

            }
        });
    }
}
