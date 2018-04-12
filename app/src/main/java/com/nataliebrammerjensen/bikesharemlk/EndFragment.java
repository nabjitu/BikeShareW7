package com.nataliebrammerjensen.bikesharemlk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nataliebrammerjensen.bikesharemlk.database.Ride;
import com.nataliebrammerjensen.bikesharemlk.database.RidesDB;

/**
 * Created by nataliebrammerjensen on 02/03/2018.
 */


public class EndFragment extends Fragment {

    private Button addRide;
    private TextView lastAdded;
    private TextView newWhat, newWhere;

    private Ride last= new Ride("", "", "", "");

    //tags
    private static final String EXTRA_RIDES_DB = "com.bignerdranch.android.geoquiz.rides_DB";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        RidesDB rdb = RidesDB.get(getActivity().getApplicationContext());
        Ride newRide = rdb.getlastAdded(getActivity());

        if (newRide.getMendRide().equals("")) {
            newWhat.setText(newRide.getMbikeName());
            newWhat.setEnabled(false);
        }
        // view products click event
        addRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((newWhat.getText().length() > 0) && (newWhere.getText().length() > 0)) {
                    RidesDB rdb = RidesDB.get(getActivity().getApplicationContext());
                    Ride newRide = rdb.getlastAdded(getActivity());
                    String endValue = newWhere.getText().toString().trim();
                    rdb.updateLastRide(rdb.getIdOnlastAdded(getActivity()), endValue);

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
        Context context = getActivity().getApplicationContext();
        RidesDB rdb = RidesDB.get(context);
        Ride newRide = rdb.getlastAdded(getActivity());

        //Set textview
        lastAdded.setText(newRide.getMbikeName() + " went from " + newRide.getMstartRide());
    }
}
