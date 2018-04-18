package com.nataliebrammerjensen.bikesharemlk.database;

import io.realm.RealmObject;

/**
 * Created by nataliebrammerjensen on 16/04/2018.
 */

public class User extends RealmObject {

    private int mId;
    private String mUserName;
    private String mPassword;
    private double credit;
    //private String mOwnsBikeNamed;

    public User(int id, String userName, String password){
        mId = id;
        mUserName = userName;

        mPassword = password;
        credit = 1000.0;
        //mOwnsBikeNamed = ownsBike;
    }

    public User(){
        mId = 0;
        mUserName = null;

        mPassword = null;
        credit = 1000.0;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    /*public void setOwnsBikeNamed(String ownsBikeNamed) {
        mOwnsBikeNamed = ownsBikeNamed;
    }*/

    public int getId() {
        return mId;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getPassword() {
        return mPassword;
    }

    /*public String getOwnsBikeNamed() {
        return mOwnsBikeNamed;
    }*/

}
