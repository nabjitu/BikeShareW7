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
 * Created by nataliebrammerjensen on 21/02/2018.
 */

public class StartFragment extends Fragment {
    private Button addRide;
    private TextView lastAdded;
    private TextView newWhat, newWhere;

    private Ride last = new Ride("", "", "", 0);

    RealmDBStuff realm;
    //RealmDBStuff rdb = new RealmDBStuff();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm=RealmDBStuff.get(getActivity().getApplicationContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_start, container, false);

        lastAdded = (TextView) v.findViewById(R.id.last_added);
        //updateUI();

        // Button
        addRide = (Button) v.findViewById(R.id.add_button);

        // Texts
        newWhat = (TextView) v.findViewById(R.id.what_text);
        newWhere =(TextView) v.findViewById(R.id.where_edit);

        // view products click event
        addRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((newWhat.getText().length()>0) && (newWhere.getText().length()>0 )){
                    //last.setMbikeName(newWhat.getText().toString().trim());
                    //last.setMstartRide(newWhere.getText().toString().trim());

                    //Isetdet for HFM
                    //add til databasen og i main activty set current til den sidst addede i databasen.
                    Ride newRide = new Ride (newWhat.getText().toString().trim(), newWhere.getText().toString().trim(), "", 0);


                    realm.writeToDB(newRide.getMbikeName(), newRide.getMstartRide(), "");

                    //HFM
                    /*Intent data=new Intent();
                    //data.setData(Uri.parse(last.toStringStart()));
                    data.setData(Uri.parse(newRide.getId().toString()));
                    getActivity().setResult(RESULT_OK, data);*/

                    // reset text fields
                    newWhat.setText(""); newWhere.setText("");
                    //updateUI();

                    getActivity().finish();
                }
            }
        });

        return v;
    }

    private void updateUI(){
        //Get last added Ride

        /*Ride newRide = rdb.getlastAdded(getActivity());

        //Set textview
        lastAdded.setText(newRide.getMbikeName() + " went from " + newRide.getMstartRide());*/
    }



}
