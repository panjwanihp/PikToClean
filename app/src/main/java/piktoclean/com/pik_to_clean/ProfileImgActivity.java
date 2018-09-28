package piktoclean.com.pik_to_clean;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileImgActivity extends AppCompatActivity {
    //private TextView msendData;

    private EditText editText;
    private Button mSaveBtn;

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


        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editText.getText().toString();

                Map<String, String> userMap = new HashMap<>();

                userMap.put("name", username);
                userMap.put("image", "image_link");

                mFirestore.collection("users").add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(ProfileImgActivity.this, "useraname added to Firebase", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        String error = e.getMessage();
                        Toast.makeText(ProfileImgActivity.this, "Error : " + error, Toast.LENGTH_SHORT).show();
                    }
                });
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

