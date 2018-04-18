package com.nataliebrammerjensen.bikesharemlk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nataliebrammerjensen.bikesharemlk.database.Ride;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity /*extends Activity*/{ // GUI variables

    private static final String TAG = "MainActivity";

    //Go to other activity
    public Button GoToStart;
    public Button GoToEnd;
    public Button showListOfRides;
    public Button GoToTest;

    public ArrayAdapter buckysAdapter;
    public ArrayAdapter buckysAdapter2;
    public ArrayAdapter buckysAdapter3;
    ArrayList<String> rideListStrings= new ArrayList<>();
    ArrayList<String> rideDates= new ArrayList<>();
    ArrayList<String> rideTimes= new ArrayList<>();
    List<Ride> mRides;

    //Current ride
    public static Ride current = new Ride("", "", "", 0);
    String currentString = current.getMstartRide();
    TextView currentRideView;

    Realm realm;
    RealmDBStuff rdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(getApplicationContext()); //Skal måske slettes?
        //realm = Realm.getDefaultInstance();
        rdb = RealmDBStuff.get(getApplicationContext());

        Log.d(TAG, "onCreate(Bundle) called");

        //DEbug
        //rdb.writeToDB("Natalie", "ITU", "");
        ArrayList<Ride> allRides = new ArrayList<>();
        allRides = rdb.getDataWithoutWhereClause();
        System.out.println("AllRides size is: " + allRides.size());

        for (Ride r: allRides){
            System.out.println("AllRides size is: " + allRides.size());
            System.out.println("======NEw Ride ======");
            System.out.println("Bike name is: " + r.getMbikeName());
            System.out.println("id is: " + r.getId());
        }
        //DEbug


        //Her sørger den for at sql databasen bliver lavet.
        //rSqlDb = RidesDB.get(getApplicationContext());

        currentRideView=(TextView) findViewById(R.id.current);;
        buckysAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rideListStrings);
        buckysAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rideDates);
        buckysAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rideTimes);

        showListOfRides = (Button) findViewById(R.id.list_rides_button);
        showListOfRides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //populateLIstView();
                Intent toy = new Intent(MainActivity.this, BikeShareActivityRidesBrowser.class);
                startActivity(toy);
            }
        });

        initStart();
        initEnd();
        initTest();
    }


    private void updateUI(String newAdd){
        currentRideView.setText(newAdd);
    }

    public void initStart() {
        GoToStart = (Button) findViewById(R.id.go_to_start_button);
        GoToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(MainActivity.this, StartRideActivity.class);
                startActivity(toy);
            }
        });
    }

    public void initEnd() {
        GoToEnd = (Button) findViewById(R.id.go_to_end_button);
        GoToEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toy = new Intent(MainActivity.this, EndRideActivity.class);
                startActivity(toy);
            }
        });
    }

    public void initTest() {
        GoToTest = (Button) findViewById(R.id.test_button);
        GoToTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent toy = new Intent(MainActivity.this, EndRideActivity.class);
                startActivity(toy);*/
                populateLIstView();
            }
        });
    }

   public void populateLIstView(){
        ArrayList<Ride> allRides = new ArrayList<>();
        allRides = rdb.getDataWithoutWhereClause();

        for (Ride r: allRides) {
            rideListStrings.add(r.getMbikeName());
            //listView2
            String day = String.valueOf(r.getmStartddmmyyyy());
            System.out.println("time = " + r.getmStarthhmmss());
            System.out.println("day = " + day);
            rideDates.add(day);
            //listView3
            String time = String.valueOf(r.getmStarthhmmss());
            System.out.println("time = " + r.getmStarthhmmss());
            System.out.println("time = " + time);
            rideTimes.add(time);

        }
        buckysAdapter.notifyDataSetChanged();
        updateUI(currentString);


        ListView buckysListView = findViewById(R.id.listView1);
        buckysListView.setAdapter(buckysAdapter);

        ListView buckysListView2 = findViewById(R.id.listView2);
        buckysListView2.setAdapter(buckysAdapter2);

        ListView buckysListView3 = findViewById(R.id.listView3);
        buckysListView3.setAdapter(buckysAdapter3);

    }

    //NDB
   public void setCrimes(List<Ride> rides) {
        mRides = rides;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}

