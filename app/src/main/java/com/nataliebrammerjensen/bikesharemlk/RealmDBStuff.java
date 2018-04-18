package com.nataliebrammerjensen.bikesharemlk;

import android.content.Context;
import android.util.Log;

import com.nataliebrammerjensen.bikesharemlk.database.Bike;
import com.nataliebrammerjensen.bikesharemlk.database.Ride;
import com.nataliebrammerjensen.bikesharemlk.database.User;

import java.util.ArrayList;
import java.util.Observable;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by nataliebrammerjensen on 12/04/2018.
 */

public class RealmDBStuff extends Observable {
    private static RealmDBStuff sRidesDB;
    static Realm realm ;
    //realm = Realm.getDefaultInstance();

    public RealmDBStuff(Context context){
        Log.d("create","db");
    }

    public static RealmDBStuff get(Context c) {
        if (sRidesDB == null) {
            sRidesDB= new RealmDBStuff(c);
            realm=Realm.getDefaultInstance();
        }
        return sRidesDB;
    }

    public void showDataWithWhereClause(int id) {
        RealmResults<Ride> rides = realm.where(Ride.class).equalTo("mId", id).findAll();
        realm.beginTransaction();
        for (Ride ride : rides) {
            System.out.println(ride.getMbikeName());
        }
        realm.commitTransaction();
    }

    public void showDataWithoutWhereClause() {
        RealmResults<Ride> rides = realm.where(Ride.class).findAll();
        realm.beginTransaction();
        for (Ride ride : rides) {
            System.out.println(ride.getMbikeName());
        }
        realm.commitTransaction();
    }

    public ArrayList<Ride> getDataWithoutWhereClause() {
        //realm.execute transaction can subvstute begin and commit trandsaction.
        RealmResults<Ride> rides = realm.where(Ride.class).findAll();
        ArrayList<Ride> result = new ArrayList<>();
        realm.beginTransaction();
        for (Ride ride : rides) {
            result.add(ride);
        }
        realm.commitTransaction();
        return result;
    }

    public void changeData(int uuid, String bikeEnd){
        Ride ride = realm.where(Ride.class).equalTo("mId", uuid).findFirst();
        ride.setMendRide(bikeEnd);
        realm.copyToRealmOrUpdate(ride);
        realm.commitTransaction();
    }

    public void writeToDB(final String bikeName, final String startRide){
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

    public void writeRideToDB(final Ride rideIn){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                //Increment ID
                /*Number currentIdNum = realm.where(Ride.class).max("mId");
                int nextId;
                if(currentIdNum == null) {
                    nextId = 1;
                } else {
                    nextId = currentIdNum.intValue() + 1;
                }
*/
                Ride ride = new Ride(12);
                //Ride ride = bgRealm.createObject(Ride.class);
                ride.setId(12);
                ride.setMbikeName(rideIn.getMbikeName());
                ride.setMstartRide(rideIn.getMstartRide());
                ride.setMstartRide(rideIn.getmStartddmmyyyy());
                ride.setMstartRide(rideIn.getmStarthhmmss());
                Log.v("Database", "Data Insertedeeee");
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

    public Ride getLastAdded(){

        final Ride result=new Ride();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
               // Number maxId = realm.where(Ride.class).max("mId");
                //Integer max = maxId.intValue();
                Ride rea = realm.where(Ride.class).equalTo("mId", 12).findFirst();
                result.setId(rea.getId());
                result.setMstartRide(rea.getMstartRide());
                result.setMendRide(rea.getMendRide());
                //Somone is missing here.
            }
        });
        return result;
    }

    public void deleteWhere(final int id){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Ride> rows = realm.where(Ride.class).equalTo("mId", id).findAll();
                rows.deleteAllFromRealm();
            }
        });
    }

    public void writeUserToDb(final User userIn){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                //Increment ID
                Number currentIdNum = realm.where(User.class).max("mId");
                int nextId;
                if(currentIdNum == null) {
                    nextId = 1;
                } else {
                    nextId = currentIdNum.intValue() + 1;
                }

                User user = bgRealm.createObject(User.class);
                user.setId(nextId);
                user.setUserName(userIn.getUserName());
                user.setPassword(userIn.getPassword());
                //user.setOwnsBikeNamed(userIn.getOwnsBikeNamed());
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

    public User lookForUserInRealm(String enteredUsername, String enteredPassword){
        User foundUser = realm.where(User.class).equalTo("username", enteredUsername).findFirst();
        if(foundUser != null){
            return foundUser;
        }
        else {
            return null;
        }
    }

    //One user can have one bike. Only the bike knows who its owner / user is.
    //Should be this way: One bike can only have 1 owner. But one User can have multiple bikes. Therefore bike has the the owner / User attribute. User donøt know which bike is related.
    public void writeBikeToDb(final Bike bikeIn){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                //Increment ID
                Number currentIdNum = realm.where(Bike.class).max("mId");
                int nextId;
                if(currentIdNum == null) {
                    nextId = 1;
                } else {
                    nextId = currentIdNum.intValue() + 1;
                }

                Bike bike = bgRealm.createObject(Bike.class);
                bike.setId(nextId);
                bike.setName(bikeIn.getName());
                bike.setOwner(bikeIn.getOwner());
                //user.set(bikeIn.getPhotoFilename());
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

    //HFM
    /*
    * To update a Ride when to add and end
    * Then use the method realm.copyOrUpdate()
    * */
}
