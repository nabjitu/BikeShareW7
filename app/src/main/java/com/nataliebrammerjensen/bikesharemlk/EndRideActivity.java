package com.nataliebrammerjensen.bikesharemlk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.nataliebrammerjensen.bikesharemlk.database.Ride;

/**
 * Created by nataliebrammerjensen on 08/02/2018.
 */

//Here is a skeleton for the BikeShareActivity.java:
public class EndRideActivity extends AppCompatActivity {

    private static final String EXTRA_RIDES_DB = "com.bignerdranch.android.geoquiz.rides_DB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout. activity_end);

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragment_container_end);
        if (fragment == null) {
            fragment = new EndFragment();
            fm.beginTransaction().add(R.id.fragment_container_end, fragment).commit();
        }
    }

}
