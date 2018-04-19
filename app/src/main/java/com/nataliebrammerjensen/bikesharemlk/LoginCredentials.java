package com.nataliebrammerjensen.bikesharemlk;

import android.content.Context;

import io.realm.Realm;

/**
 * Created by nataliebrammerjensen on 19/04/2018.
 */

public class LoginCredentials {
    private static LoginCredentials lc;
    private String username;

    public LoginCredentials(Context context){

    }

    public LoginCredentials(String usernameIn){
        username = usernameIn;
    }

    public static LoginCredentials get(Context c) {
        if (lc == null) {
            lc= new LoginCredentials(c);
        }
        return lc;
    }

    public String getLoggedInUsername(){
        return username;
    }

    public void setUsername(String newUsername){
        username = newUsername;
    }

}
