package piktoclean.com.pik_to_clean.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.protobuf.compiler.PluginProtos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import piktoclean.com.pik_to_clean.MainActivity;
import piktoclean.com.pik_to_clean.R;
import piktoclean.com.pik_to_clean.ShowCamera;
import piktoclean.com.pik_to_clean.classification;

/**
 * A simple {@link Fragment} subclass.
 */
public class CamFragment extends Fragment {
    private android.hardware.Camera camera;
    private static final int REQUEST_LOCATION = 1;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private FrameLayout fl;
    private ShowCamera showCamera;
    private Context c;
    private FloatingActionButton fab;
    LocationManager locationManager;
    String lattitude,longitude;
    private View v2;
    public CamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        c=inflater.getContext();

        return inflater.inflate(R.layout.fragment_cam, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fl=(FrameLayout) getView().findViewById(R.id.camlayout);
        fab=getView().findViewById(R.id.cap);
        camera= android.hardware.Camera.open();
        showCamera = new ShowCamera(c,camera);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},MY_CAMERA_REQUEST_CODE);
        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        fl.addView(showCamera);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v2=v;
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();

                } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    String check = getLocation();
                    if (check == "") {
                    } else {
                        captureImage(v);
                    }
                }

            }
        });
    }


    android.hardware.Camera.PictureCallback mPictureCallback = new android.hardware.Camera.PictureCallback() {


        @Override
        public void onPictureTaken(byte[] data, android.hardware.Camera camera) {

           Log.d("ij","ij"+data.toString());
            Intent i=new Intent(c,classification.class);
            i.putExtra("pic",data);
            startActivity(i);
        }
    };


    private String getLocation() {
        if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return "";
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Toast.makeText(c, "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude, Toast.LENGTH_SHORT).show();
                return "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude;
            } else if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Toast.makeText(c, "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude, Toast.LENGTH_SHORT).show();
                return "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude;

            } else if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                Toast.makeText(c, "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude + "", Toast.LENGTH_SHORT).show();
                return "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude;
            } else {

                Toast.makeText(c, "Unble to Trace your location", Toast.LENGTH_SHORT).show();
                return "";
            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    public void captureImage(View v){
         if(camera!=null){
             camera.takePicture(null,null,mPictureCallback);
         }
    }
}
