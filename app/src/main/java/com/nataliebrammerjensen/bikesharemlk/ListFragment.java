package com.nataliebrammerjensen.bikesharemlk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.nataliebrammerjensen.bikesharemlk.database.Ride;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import io.realm.Realm;

public class ListFragment extends Fragment implements Observer {
  private static RealmDBStuff sharedRides;

  //View to list all rides
  private RecyclerView mRidesList;
  private RidesAdapter mAdapter;

  private Realm realm;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    sharedRides = new RealmDBStuff(getActivity().getApplicationContext());
    sharedRides.addObserver(this);

    Realm.init(getActivity().getApplicationContext());
    realm = Realm.getDefaultInstance();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v= inflater. inflate(R.layout.fragment_list, container, false);

    //NEJH
    ArrayList<String> fromStrings = new ArrayList<>();

    ArrayList<Ride> rides = sharedRides.getDataWithoutWhereClause() ;
    for (Ride r : rides){
      Log.d("nat",r.toString());
    }
    mAdapter = new RidesAdapter(rides);
    //NEJH

    //JH
    //mAdapter = new RidesAdapter(sharedRides.getAllRides());
    mRidesList= v.findViewById(R.id.list_recycler_view);
    mRidesList.setLayoutManager(new LinearLayoutManager(getActivity()));
    mRidesList.setAdapter(mAdapter);
    //JH

    // TODO set up adapter
    //updateUI();

    return v;
  }

  @Override
  public void update(Observable observable, Object data) {
    //updateListOfThings();
    mAdapter.notifyDataSetChanged();
  }

  //Det her er ligsom en CrimeHolder
  public class RideHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mWhatTextView, mStartTextView, mEndTextView;
    private TextView mFromTextView;
    private TextView mToTextView;
    private TextView mDateTextView;
    private TextView mStartTimeTextView;
    private TextView mEndTimeTextView;

    public Ride mRide;

    public RideHolder(View v){
      super(v);

      //Chap 9
      //itemView.setOnClickListener(this);


      //Side 182
      //mWhatTextView = (TextView) iv

      //Stackoverflow
      /*LinearLayout ll = (LinearLayout) view; // get the parent layout view
      TextView tv = (TextView) ll.findViewById(R.id.contents); // get the child text view
      final String text = tv.getText().toString();*/

      //N
      mWhatTextView = (TextView) v.findViewById(R.id.what_bike_ride);
      mFromTextView = (TextView) v.findViewById(R.id.where_from_ride);
      //mToTextView = (TextView) iv.findViewById(R.id.where_to_ride);
      //mDateTextView = (TextView) iv.findViewById(R.id.date_of_ride);
      //mStartTimeTextView = (TextView) iv.findViewById(R.id.time_of_start);
      //mEndTimeTextView = (TextView) iv.findViewById(R.id.time_of_end);

      v.setOnClickListener(this);

    }

    public void bind(Ride ride){
      mRide= ride;
      // TODO set contents of a row
      //Side 189 When given a Crime, RideHolder will now update the title TextView, date TextView, and solved CheckBox to reflect the state of the Crime.
      mWhatTextView.setText(mRide.getMbikeName());
      System.out.println("Binding Ride ...: " + mRide.getMbikeName());
      /*mFromTextView.setText(mRide.getMstartRide());
      mDateTextView.setText(mRide.getmStartddmmyyyy());
      mStartTimeTextView.setText(mRide.getmStarthhmmss());*/
      //mEndTimeTextView.setText(mRide.getHhmmss());
    }

    @Override
    public void onClick(View v) {
        sharedRides.deleteWhere( mRide.getId());
        //Chap 9
        Toast.makeText(getActivity(), mRide.getMbikeName() + " clicked!", Toast.LENGTH_SHORT).show();

        refresh(this.getAdapterPosition());
    }
  }

  private class RidesAdapter extends RecyclerView.Adapter<RideHolder> {
    private  List<Ride> mRides;



    public RidesAdapter(List<Ride> rides){
      mRides= rides;
    }

    //Side 188
    @Override
    public RideHolder onCreateViewHolder(ViewGroup parent, int viewType){
      LayoutInflater layoutInflater= LayoutInflater.from(getActivity());
      View v=layoutInflater.inflate(R.layout.list_item,parent,false);
      return new RideHolder(v);
     }

    @Override
    public void onBindViewHolder(RideHolder holder, int position) {
      Ride ride= mRides.get(position);
      holder.bind(ride);
    }

    @Override
    public int getItemCount() {
      return mRides.size();
    }
  }

  private void updateUI() {

    List<Ride> rides = sharedRides.getDataWithoutWhereClause() ;
    mAdapter = new RidesAdapter(rides);
    //mCrimeRecyclerView.setAdapter(mAdapter); //Put the adapter on the view.

    //Skal måske have notifydatasetchanged

    //if crimelab == null log to logcat "it is empty"
  }

  public void refresh(int pos){
    mAdapter.mRides.remove(pos);
    mAdapter.notifyDataSetChanged();
  }

}
