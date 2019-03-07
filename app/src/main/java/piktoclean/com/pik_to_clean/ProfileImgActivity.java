package piktoclean.com.pik_to_clean;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import io.opencensus.tags.Tag;

public class ProfileImgActivity extends AppCompatActivity {
    //private TextView msendData;

    private EditText editText;
    private Button mSaveBtn;
    private  String uid;
    private SharedPreferences mpreference;
    private SharedPreferences.Editor mEditor;

    private FirebaseFirestore mFirestore;

    ImageView mImageViiew;
    Button mChooseBtn;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;




    // private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener firebaseAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_img);

        editText = (EditText) findViewById(R.id.editText69);
        mSaveBtn = (Button) findViewById(R.id.btnSubmit);

        mImageViiew = findViewById(R.id.imageView3);

        mImageViiew.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        pickImageFromGallery();
                    }
                }
                else {
                    pickImageFromGallery();
                }
            }
        });
        mFirestore = FirebaseFirestore.getInstance();
        Log.d("user name", "here finally 1");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("user name", "here finally");
        if (user != null) {
            String name = user.getDisplayName();

                // The user's ID, unique to the Firebase project. Do NOT use this value to
                // authenticate with your backend server, if you have one. Use
                // FirebaseUser.getIdToken() instead.
            Log.d("user name", "not null:"+name);

            if (name != null) {
                editText.setText(user.getDisplayName());
                Log.d("user name", "not null: ");
            }
                uid = user.getUid();
        }

        else{
            String name = null;
            String uid = null;
        }
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editText.getText().toString();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("username", "User profile updated.");
                                }
                            }
                        });
                mpreference = getSharedPreferences("piktoclean.com.pik_to_clean", Context.MODE_PRIVATE);
                mEditor = mpreference.edit();
                mEditor.putString("name",username);
                mEditor.putString("uid",uid);
                mEditor.commit();
                Intent intent = new Intent(ProfileImgActivity.this, NavBarActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void pickImageFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            mImageViiew.setImageURI(data.getData());
        }
    }
}

