package com.nataliebrammerjensen.bikesharemlk;

import android.support.v4.app.Fragment;

import android.widget.ImageButton;
import android.widget.ImageView;

import com.nataliebrammerjensen.bikesharemlk.database.Bike;

import java.io.File;

/**
 * Created by nataliebrammerjensen on 13/04/2018.
 */

public class BikeDetailsFragment extends Fragment {
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private File mPhotoFile;
    private Bike mBike;

    private static final int REQUEST_PHOTO= 2;

    //Page 298
    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String bikeId = getArguments().getSerializable(ARG_Bike_ID);
        mBike = RidesDB.get(getActivity()).getBike(bikeId);
        mPhotoFile = RidesDB.get(getActivity()).getPhotoFile(mBike);
    }*/

    /*public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bike_details, container, false);
        mPhotoButton = (ImageButton) v.findViewById(R.id.crime_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null && captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);
        if (canTakePhoto) {
            Uri uri = Uri.fromFile(mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = (ImageView) v.findViewById(R.id.crime_photo);
        return v;
    }*/
}
