package piktoclean.com.pik_to_clean.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import piktoclean.com.pik_to_clean.LoginActivity;
import piktoclean.com.pik_to_clean.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private Context c;
    private Button btn;
    private LinearLayout login_enable,login_disable;
    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        c=inflater.getContext();
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn = (Button) getView().findViewById(R.id.button);
        login_enable = (LinearLayout) getView().findViewById(R.id.login_enable);
        login_disable = (LinearLayout) getView().findViewById(R.id.login_disable);
        SharedPreferences pref = c.getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        SharedPreferences mpreference = c.getSharedPreferences("piktoclean.com.pik_to_clean", Context.MODE_PRIVATE);
        String name= mpreference.getString("user","");
        if(name!="") {

           login_disable.setVisibility(View.INVISIBLE);
        }else{
            login_enable.setVisibility(View.INVISIBLE);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(c, LoginActivity.class));
                    getActivity().finish();
                }
            });
        }
    }
}

