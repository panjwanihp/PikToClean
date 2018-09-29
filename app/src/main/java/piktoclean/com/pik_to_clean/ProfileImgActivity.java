package piktoclean.com.pik_to_clean;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    // private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener firebaseAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_img);

        mFirestore = FirebaseFirestore.getInstance();

        editText = (EditText) findViewById(R.id.tvValue);
        mSaveBtn = (Button) findViewById(R.id.btnSubmit);

        Log.d("user name", "here finally 1");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("user name", "here finally");

        if (user != null) {
            // Name, email address, and profile photo Url
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
//                Map<String, String> userMap = new HashMap<>();
//
//                userMap.put("name", username);
//                userMap.put("image", "image_link");

//                mFirestore.collection("users").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//
//                        Toast.makeText(ProfileImgActivity.this, "useraname added to Firebase", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                        String error = e.getMessage();
//                        Toast.makeText(ProfileImgActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

        });


        //  mAuth = FirebaseAuth.getInstance();

        //firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
  /*          @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(ProfileImg.this, NavBarActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };
*/

/*      //  msendData = (TextView)findViewById(R.id.usrname);

    submit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    });
*/
    }
}

