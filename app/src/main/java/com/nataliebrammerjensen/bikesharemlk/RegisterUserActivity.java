package com.nataliebrammerjensen.bikesharemlk;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nataliebrammerjensen.bikesharemlk.database.Bike;
import com.nataliebrammerjensen.bikesharemlk.database.User;

import io.realm.Realm;

public class RegisterUserActivity extends AppCompatActivity {


    private Button btnViewData;
    public Button GoToViewData;
    Realm realm;
    RealmDBStuff rdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout. activity_register_user);


        /* FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragment_container_register);
        if (fragment == null) {
            fragment = new StartFragment();
            fm.beginTransaction().add(R.id.fragment_container_register, fragment).commit();
        }
        */

        //======Fragments is a bith======

        rdb = new RealmDBStuff(getApplicationContext());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final EditText etUsername = (EditText) findViewById(R.id.user_name);
        final EditText etPassword = (EditText) findViewById(R.id.password);
        final EditText bikeName = (EditText) findViewById(R.id.bike_name);
        final EditText bikeBrand = (EditText) findViewById(R.id.bike_brand);
        final EditText bikePrice = (EditText) findViewById(R.id.bike_hour_price);
        final Button bRegister = (Button) findViewById(R.id.register_button);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                User newUser = new User(0, username, password);

                final String bName = bikeName.getText().toString();
                final String bBrand = bikeBrand.getText().toString();
                final String bPrice = bikeBrand.getText().toString();

                Integer price = 0;

                try{
                    price = Integer.parseInt(bPrice);
                } catch (Exception e){
                    Toolbox.doToast("Price has to be of type double", getApplicationContext());
                    e.printStackTrace();
                }

                //Because I had to initialize price to 0 I have to make sure that it has the given value instead of 0.
                if(price!= 0){
                    Bike usersBike = new Bike(0, bName, username, bBrand, true, price);
                    rdb.writeBikeToDb(usersBike);
                } else{
                    Toolbox.doToast("v2 Price has to be of type double", getApplicationContext());
                }

                rdb.writeUserDataToDb(newUser.getUserName(), newUser.getPassword());
                    /*if() {
                        Toolbox.doToast("User was not created", getApplicationContext());
                    }
                     else {
                        Toolbox.doToast("Wuhuuu.. User was created", getApplicationContext());
                    }*/
            }
        });
        //===============================

    }
}
