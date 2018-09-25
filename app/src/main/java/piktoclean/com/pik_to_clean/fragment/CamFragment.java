package piktoclean.com.pik_to_clean.fragment;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import piktoclean.com.pik_to_clean.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CamFragment extends Fragment {


    public CamFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public CamFragment(Bitmap bitmap) {
        // Required empty public constructor
        Log.d("jh", "CamFragment: "+bitmap);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cam, container, false);
    }

}
