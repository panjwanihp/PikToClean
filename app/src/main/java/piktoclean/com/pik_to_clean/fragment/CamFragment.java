package piktoclean.com.pik_to_clean.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import piktoclean.com.pik_to_clean.R;
import piktoclean.com.pik_to_clean.ShowCamera;

/**
 * A simple {@link Fragment} subclass.
 */
public class CamFragment extends Fragment {
    private android.hardware.Camera camera;
    private FrameLayout fl;
    private ShowCamera showCamera;
    private Context c;

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
        camera= android.hardware.Camera.open();
        showCamera = new ShowCamera(c,camera);
    }
}
