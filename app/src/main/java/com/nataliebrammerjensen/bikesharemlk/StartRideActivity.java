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

public class StartRideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout. activity_start);

        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new StartFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
