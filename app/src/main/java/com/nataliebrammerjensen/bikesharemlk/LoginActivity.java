package com.nataliebrammerjensen.bikesharemlk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nataliebrammerjensen.bikesharemlk.database.User;

import io.realm.Realm;

/**
 * Created by nataliebrammerjensen on 17/04/2018.
 */

public class LoginActivity extends AppCompatActivity {

    Realm realm;
    RealmDBStuff rdb;

    String username;
    String password;

    TextView usernameTV;
    TextView passwordTV;

    Button loginBtn;
    Button registerBtn;

    private static final String TAG = "USERNAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout. activity_login);

        /*FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = fm.findFragmentById(R.id.fragment_container_login);
        if (fragment == null) {
            fragment = new StartFragment();
            fm.beginTransaction().replace(R.id.fragment_container_login, fragment).commit(); //Erstat add med replace
        }*/

        //== Fragments is a bicth==

        loginBtn = (Button) findViewById(R.id.login_button);
        usernameTV = (TextView) findViewById(R.id.login_username);
        passwordTV =(TextView) findViewById(R.id.login_password);
        registerBtn =  findViewById(R.id.register_button);

        // view products click event
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((usernameTV.getText().length()>0) && (passwordTV.getText().length()>0 )){
                    username = usernameTV.getText().toString().trim();
                    password = passwordTV.getText().toString().trim();

                    User foundUser = rdb.lookForUserInRealm(username, password);

                    if(foundUser != null){

                        Intent toy = new Intent(LoginActivity.this, MainActivity.class);
                        toy.putExtra(TAG, username);
                        startActivity(toy);
                    }

                    //getActivity().finish();
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toy = new Intent(LoginActivity.this, RegisterUserActivity.class);
                toy.putExtra(TAG, username);
                startActivity(toy);
            }
        });


        //==========================
    }
}
