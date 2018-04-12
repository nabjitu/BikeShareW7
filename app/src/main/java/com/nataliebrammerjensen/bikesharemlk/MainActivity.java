package com.nataliebrammerjensen.bikesharemlk;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nataliebrammerjensen.bikesharemlk.database.Ride;
import com.nataliebrammerjensen.bikesharemlk.database.RidesDB;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends Activity /*implements View.OnClickListener*/ { // GUI variables

    private static final String TAG = "MainActivity";
    public static RidesDB rSqlDb;

    //Go to other activity
    public Button GoToStart;
    public Button GoToEnd;
    public Button showListOfRides;

    public ArrayAdapter buckysAdapter;
    public ArrayAdapter buckysAdapter2;
    public ArrayAdapter buckysAdapter3;
    ArrayList<String> rideListStrings= new ArrayList<>();
    ArrayList<String> rideDates= new ArrayList<>();
    ArrayList<String> rideTimes= new ArrayList<>();
    //UUID FuuidNew;

    //NDB
    List<Ride> mRides;

    //Current ride
    public static Ride current = new Ride("", "", "", "");
    String currentString = current.getMstartRide();
    TextView currentRideView ;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout. activity_main);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();

        Log.d(TAG, "onCreate(Bundle) called");

        //DEbug
        //writeToDB("lala", "wuhu");
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
                populateLIstView();
                /*Intent toy = new Intent(MainActivity.this, BikeShareActivityRidesBrowser.class);
                startActivity(toy);*/
            }
        });

        initStart();
        initEnd();
        populateLIstView();
    }
    //Den her er nok ikke relevant for mig eller realm
    /*public void onClick(){
        writeToDB(name.getText().toString().trim(), Integer.parseInt(marks.getText().toString().trim()));
    }*/

    //Det her svarer til SQL select * Where statement
    /*public void showData(){
        RealmResults<Ride> rides = realm.where(Ride.class).equalTo("invited", false).findAll();

        realm.beginTransaction();
        for (Ride ride : rides) {
            rides.setInvited(true);

        }
    }*/

    public void showDataWithWhereClause(String uuid) {
        RealmResults<Ride> rides = realm.where(Ride.class).equalTo("uuid", uuid).findAll();
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
        RealmResults<Ride> rides = realm.where(Ride.class).findAll();
        ArrayList<Ride> result = new ArrayList<>();
        realm.beginTransaction();
        for (Ride ride : rides) {
            result.add(ride);
        }
        realm.commitTransaction();
        return result;
    }

    public void changeData(String uuid, String bikeEnd){
        RealmResults<Ride> rides = realm.where(Ride.class).equalTo("uuid", uuid).findAll();

        realm.beginTransaction();
        for (Ride ride : rides) {
            ride.setMendRide(bikeEnd);
        }
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

    public void populateLIstView(){
        //NDB
        //DebugDB.getAddressLog();

        /*rSqlDb = RidesDB.get(getApplicationContext());
        List<Ride> rides = rSqlDb.getRidesDB();
//        List<String> rideListStrings = new ArrayList<>();
        for (Ride r: rides) {
            if(!r.getMendRide().equals("")){
                rideListStrings.add(r.toString());
                System.out.println(r.toString());
            }

        }

        ListView buckysListView = findViewById(R.id.listView);
        buckysListView.setAdapter(buckysAdapter);*/

        //NDB
        rSqlDb = RidesDB.get(getApplicationContext());
        ArrayList<Ride> sqlRides = getDataWithoutWhereClause();

        for (Ride r: sqlRides) {
            /*if(!r.getMendRide().equals("")){*/
                rideListStrings.add(r.getMstartRide());
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
            //}
        }

        //Virker rigtig godt, men skal erstattes a REALM.
        /*for (Ride r: sqlRides) {
            if(!r.getMendRide().equals("")){
                rideListStrings.add(r.getMstartRide());
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
        }*/

        /*ArrayList<Ride> rides =(ArrayList<Ride>) RidesDB.get(getApplicationContext()).getRidesDB();
        rideListStrings.clear(); // clear so that we don't have both the old and the new in this list.
        for(Ride r : rides) {
            rideListStrings.add(r.toString());
            //W5
            String day = String.valueOf(r.getmStartddmmyyyy());
            rideDates.add(day);
            System.out.println(r.getmStartddmmyyyy());
        }*/
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
        realm.close();
    }
}

