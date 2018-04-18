package com.nataliebrammerjensen.bikesharemlk;

import android.app.Fragment;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.nataliebrammerjensen.bikesharemlk.database.Bike;
import com.nataliebrammerjensen.bikesharemlk.database.User;

import io.realm.Realm;

/**
 * Created by nataliebrammerjensen on 17/04/2018.
 */

public class RegisterUserFragment extends Fragment {

    private Button btnViewData;
    public Button GoToViewData;
    Realm realm;
    RealmDBStuff rdb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rdb = new RealmDBStuff(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register_user, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final EditText etUsername = (EditText) v.findViewById(R.id.user_name);
        final EditText etPassword = (EditText) v.findViewById(R.id.password);
        final EditText bikeName = (EditText) v.findViewById(R.id.bike_name);
        final EditText bikeBrand = (EditText) v.findViewById(R.id.bike_brand);
        final EditText bikePrice = (EditText) v.findViewById(R.id.bike_hour_price);
        final Button bRegister = (Button) v.findViewById(R.id.register_button);

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
                    Toolbox.doToast("Price has to be of type double", getActivity().getApplicationContext());
                    e.printStackTrace();
                }

                //Because I had to initialize price to 0 I have to make sure that it has the given value instead of 0.
                if(price!= 0){
                    Bike usersBike = new Bike(0, bName, username, bBrand, true, price);
                    rdb.writeBikeDataToDb(usersBike.getName(), usersBike.getOwner(), usersBike.getBrand());
                } else{
                    Toolbox.doToast("v2 Price has to be of type double", getActivity().getApplicationContext());
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
        return v;
    }

    /*
    private boolean networkPermission() {
        String permission = "android.permission.REQUEST_INTERNET_PERMISSION";
        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
        return res != PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_INTERNET_PERMISSION);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_ACCESS_NETWORK_STATE_PERMISSION);
    }*/
}
