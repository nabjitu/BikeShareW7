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
    //realm = realm.getDefaultInstance(); //Malik siger mp ikke være der.

    public RealmDBStuff(Context context){
        Log.d("create","db");
    }

    public static RealmDBStuff get(Context c) {
        if (sRidesDB == null) {
            sRidesDB= new RealmDBStuff(c);
            //realm.init(c); //Tilføjet efter maliks hjælp. SKal nok slettes.
            realm = Realm.getDefaultInstance();
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

    //Works
    public void writeToDB(final String bikeName, final String startRide, final String endRide){
        final int id  = getSizeOfDB();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Ride ride = bgRealm.createObject(Ride.class);
                System.out.println("Creating Ride object in REalm: with name: " + bikeName + " and id " + id);
                ride.setId(id);
                ride.setMbikeName(bikeName);
                ride.setMstartRide(startRide);
                ride.setMendRide(startRide);
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

    //Doesn't work
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
        final int max = getSizeOfDB();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
               // Number maxId = realm.where(Ride.class).max("mId");
                //Integer max = maxId.intValue();
                RealmResults<Ride> realmResult = realm.where(Ride.class).equalTo("mId", max).findAll();
                Ride rea = realmResult.get(0);
                System.out.println("getLastAdded = Ride: " + rea.getMbikeName() + " " + rea.getId());
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

    public int getSizeOfDB(){
        ArrayList<Ride> allRides = new ArrayList<>();
        allRides = getDataWithoutWhereClause();
        return allRides.size();
    }

    //====== User======

    public ArrayList<User> getAllUsersWithoutWhereClause() {
        //realm.execute transaction can subvstute begin and commit trandsaction.
        RealmResults<User> users = realm.where(User.class).findAll();
        ArrayList<User> result = new ArrayList<>();
        realm.beginTransaction();
        for (User user : users) {
            result.add(user);
        }
        realm.commitTransaction();
        return result;
    }

    public int getSizeOfUserDB(){
        ArrayList<User> allUsers = new ArrayList<>();
        allUsers = getAllUsersWithoutWhereClause();
        return allUsers.size();
    }

    public void writeUserDataToDb(final String mUserName, final String mPassword){
        //final int id  = getSizeOfUserDB();
        //realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                User user = bgRealm.createObject(User.class);
                System.out.println("Creating User object in REalm: with name: " + mUserName + " and id " /*+ id*/);
                //user.setId(id);
                user.setUserName(mUserName);
                user.setPassword(mPassword);
                user.setCredit(100);
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

    public User lookForUserInRealm(String enteredUsername){
        User foundUser = realm.where(User.class).equalTo("mUserName", enteredUsername).findFirst();
        if(foundUser != null){
            return foundUser;
        }
        else {
            return null;
        }
    }



    //====== Bike ======

    public ArrayList<Bike> getAllBikesWithoutWhereClause() {
        //realm.execute transaction can subvstute begin and commit trandsaction.
        RealmResults<Bike> rides = realm.where(Bike.class).findAll();
        ArrayList<Bike> result = new ArrayList<>();
        realm.beginTransaction();
        for (Bike bike : rides) {
            result.add(bike);
        }
        realm.commitTransaction();
        return result;
    }

    public int getSizeOfBikeDB(){
        ArrayList<Bike> allBikes = new ArrayList<>();
        allBikes = getAllBikesWithoutWhereClause();
        return allBikes.size();
    }

    public void writeBikeDataToDb(final String mBikeName, final String ownerName, final String brand){
        final int id  = getSizeOfBikeDB();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Bike bike = bgRealm.createObject(Bike.class);
                System.out.println("Creating Bike object in REalm: with name: " + mBikeName + " and id " + id);
                bike.setId(id);
                bike.setName(mBikeName);
                bike.setOwner(ownerName);
                bike.setAvailable(true);
                bike.setBrand(brand);
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
