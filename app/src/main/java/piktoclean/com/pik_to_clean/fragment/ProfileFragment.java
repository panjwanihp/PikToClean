package piktoclean.com.pik_to_clean.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import piktoclean.com.pik_to_clean.CustomAdapter;
import piktoclean.com.pik_to_clean.DataModel;
import piktoclean.com.pik_to_clean.LoginActivity;
import piktoclean.com.pik_to_clean.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    private Context c;
    private Button btn;
    private LinearLayout login_enable,login_disable;
    private TextView nam,phoneno;
    private ImageView dp;
    private String name;
    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    private String address;
    private Uri picuri;
    private String username;
    private int count=0;
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        nam= getView().findViewById(R.id.name);
        phoneno=getView().findViewById(R.id.number);
        dp=getView().findViewById(R.id.dp);
        nam.setText(user.getDisplayName());
        listView=(ListView)getView().findViewById(R.id.list);

        dataModels= new ArrayList<>();
        SharedPreferences pref = c.getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        SharedPreferences mpreference = c.getSharedPreferences("piktoclean.com.pik_to_clean", Context.MODE_PRIVATE);
        name= mpreference.getString("user","");
        phoneno.setText(name);
        username=mpreference.getString("name","");
        dp.setImageURI(user.getPhotoUrl()) ;
        if(name!="") {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference GTdriver = db.collection("Garbage");
            GTdriver.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for (QueryDocumentSnapshot d:task.getResult()){

                                if(d.getString("Uphone").equals(name)){
                                    Geocoder geocoder;
                                    List<Address> addresses;
                                    geocoder = new Geocoder(c, Locale.getDefault());

                                    try {
                                        addresses = geocoder.getFromLocation(d.getGeoPoint("Location").getLatitude(), d.getGeoPoint("Location").getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                        address = addresses.get(0).getAddressLine(0)+","+addresses.get(0).getLocality();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    FirebaseStorage storage = FirebaseStorage.getInstance();

                                    StorageReference storageRef = storage.getReferenceFromUrl("gs://piktoclean.appspot.com/images/").child(d.getId());
                                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            picuri=uri;
                                            //Handle whatever you're going to do with the URL here
                                        }
                                    });
                                    dataModels.add(new DataModel(d.getString("Dname"), d.getString("Dphone"), address,picuri));
                                    count++;
                                }
                            adapter= new CustomAdapter(dataModels,getContext());
                            listView.setAdapter(adapter);
                            if(count>0){
                                TextView v=getView().findViewById(R.id.stat);
                                v.setText("Queries");
                            }

                        }

                    }
                }

            });

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

