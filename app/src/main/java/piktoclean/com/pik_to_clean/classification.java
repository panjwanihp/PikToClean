package piktoclean.com.pik_to_clean;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import io.grpc.Context;

public class classification extends AppCompatActivity {
    private ImageView im;

    private Button proceedbtn;
    FirebaseFirestore firebaseFirestore  = FirebaseFirestore.getInstance();

    StorageReference storageReference;
    FirebaseAuth.AuthStateListener mAuth;
    FirebaseUser user;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;
    private ImageView imageView;


    private SharedPreferences mpreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);
        im=findViewById(R.id.slide_image);
        Intent i=getIntent();

        proceedbtn = (Button) findViewById(R.id.proceed);
        byte[] bytes=i.getByteArrayExtra("pic");
        Bitmap picture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        im.setImageBitmap(picture);
        File photo = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
        //  File photo = new File(getCacheDir(), "picture.jpg");
        Uri imageuri = Uri.fromFile(photo);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        mAuth = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            }
        };

        mpreference = getSharedPreferences("piktoclean.com.pik_to_clean", android.content.Context.MODE_PRIVATE);

          proceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=mpreference.getString("name","harsh");

                Log.d("uppload", "onClick: "+name);
                if (name != null) {
                    Log.d("first if", "onClick: ");
                    if (filePath != null) {
                        Log.d("nested if", "onClick: ");
                        uplodImageg();
                    }
                    else{
                        Log.d("nested else", "onClick: ");
                    }
                }
                else{
                    Log.d("outer if", "onClick: ");
                }
                Toast.makeText(classification.this, "Uploading.....", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(classification.this,NavBarActivity.class);
                startActivity(i);
                finish();

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null ){

            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    private void uplodImageg() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
        ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Toast.makeText(classification.this, "Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(classification.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                        .getTotalByteCount());
                progressDialog.setMessage("Uploaded "+(int)progress+"%");

            }
        });


    }
}
