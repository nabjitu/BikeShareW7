package com.nataliebrammerjensen.bikesharemlk;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nataliebrammerjensen.bikesharemlk.database.Ride;
import com.nataliebrammerjensen.bikesharemlk.database.User;

import io.realm.Realm;

/**
 * Created by nataliebrammerjensen on 17/04/2018.
 */

public class LoginFragment extends Fragment{

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rdb = new RealmDBStuff(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        loginBtn = (Button) v.findViewById(R.id.login_button);
        usernameTV = (TextView) v.findViewById(R.id.login_username);
        passwordTV =(TextView) v.findViewById(R.id.login_password);

        // view products click event
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((usernameTV.getText().length()>0) && (passwordTV.getText().length()>0 )){
                    username = usernameTV.getText().toString().trim();
                    password = passwordTV.getText().toString().trim();

                    User foundUser = rdb.lookForUserInRealm(username, password);

                    if(foundUser != null){

                        Intent toy = new Intent(getActivity(), MainActivity.class);
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
                Intent toy = new Intent(getActivity(), RegisterUserActivity.class);
                toy.putExtra(TAG, username);
                startActivity(toy);
            }
        });

        return v;
    }
}
