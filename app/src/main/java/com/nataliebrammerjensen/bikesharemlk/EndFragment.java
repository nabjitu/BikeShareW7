package com.nataliebrammerjensen.bikesharemlk;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nataliebrammerjensen.bikesharemlk.database.Ride;

import io.realm.Realm;

/**
 * Created by nataliebrammerjensen on 02/03/2018.
 */


public class EndFragment extends Fragment {

    private Button addRide;
    private TextView lastAdded;
    private TextView newWhat, newWhere;

    private Ride last= new Ride("", "", "", 0);

    RealmDBStuff realm;

    //tags
    private static final String EXTRA_RIDES_DB = "com.bignerdranch.android.geoquiz.rides_DB";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = RealmDBStuff.get(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_end, container, false);

        lastAdded = (TextView) v.findViewById(R.id.last_added_end);
        updateUI();

        // Button
        addRide = (Button) v.findViewById(R.id.add_button_end);

        // Texts
        newWhat = (TextView) v.findViewById(R.id.what_text_end);
        newWhere = (TextView) v.findViewById(R.id.where_edit_end);

        final Ride newRide = realm.getLastAdded();
        System.out.println("Adding end... LastAdded is: " + newRide.getMbikeName() + " " + newRide.getMendRide() + newRide.getId());

        if (newRide.getMendRide().equals("")) {
            newWhat.setText(newRide.getMbikeName());
            newWhat.setEnabled(false);
        }
        // view products click event
        addRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((newWhat.getText().length() > 0) && (newWhere.getText().length() > 0)) {
                    String endValue = newWhere.getText().toString().trim();
                    realm.changeData(newRide.getId(), endValue);

                    // reset text fields
                    newWhat.setText("");
                    newWhere.setText("");
                    updateUI();

                    getActivity().finish();
                }
            }
        });
        return v;
    }

    private void updateUI(){
        //Get last added Ride

        Ride newRide = realm.getLastAdded();

        //Set textview
        lastAdded.setText(newRide.getMbikeName() + " went from " + newRide.getMstartRide());
    }
}
