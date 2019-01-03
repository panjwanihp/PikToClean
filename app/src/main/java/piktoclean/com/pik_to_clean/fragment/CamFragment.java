package piktoclean.com.pik_to_clean.fragment;


import android.annotation.SuppressLint;
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


import com.google.protobuf.compiler.PluginProtos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import piktoclean.com.pik_to_clean.R;
import piktoclean.com.pik_to_clean.ShowCamera;
import piktoclean.com.pik_to_clean.classification;

/**
 * A simple {@link Fragment} subclass.
 */
public class CamFragment extends Fragment {
    private android.hardware.Camera camera;
    private FrameLayout fl;
    private ShowCamera showCamera;
    private Context c;
    private FloatingActionButton fab;
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
        fl.addView(showCamera);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage(v);
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



    public void captureImage(View v){
         if(camera!=null){
             camera.takePicture(null,null,mPictureCallback);
         }
    }
}
