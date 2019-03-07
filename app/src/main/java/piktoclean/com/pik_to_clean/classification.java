package piktoclean.com.pik_to_clean;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.TextView;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

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
    private TextView status;
    private Intent i;
    private byte[] bytes;
    private SharedPreferences mpreference;
    private int flag;
    private Bitmap picture;
    private int[] pixels;
    private SharedPreferences pref;
    private int picnum;
    private int[][] point={{80,200,130,80},{80,150,180,80},{120,200,180,80},{80,240,160,80}};
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);
        im=findViewById(R.id.slide_image);
        status=findViewById(R.id.status);
        pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
        picnum=pref.getInt("picnum",0);
        SharedPreferences.Editor ed = pref.edit();
        ed.putBoolean("activity_executed", true);
        if(picnum>=3){
            ed.putInt("picnum", 0);
        }else {
            ed.putInt("picnum", picnum + 1);
        }
        ed.commit();
        i=getIntent();
        proceedbtn = (Button) findViewById(R.id.proceed);
        bytes=i.getByteArrayExtra("pic");
        flag=i.getIntExtra("flag",0);
        picture = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        picture = picture.copy( Bitmap.Config.ARGB_8888 , true);
        pixels = new int[picture.getHeight()*picture.getWidth()];
        picture.getPixels(pixels, 0,picture.getWidth(), 0, 0, picture.getWidth(), picture.getHeight());
        if(flag==0){
            status.setText("Garbage Detected");
            for (int x=320*point[picnum][0]-point[picnum][2]; x<320*point[picnum][0]-point[1][3]; x++)
                pixels[x] = Color.RED;
            for (int x=320*point[picnum][1]-point[picnum][2]; x<320*point[picnum][1]-point[1][3]; x++)
                pixels[x] = Color.RED;
            for (int x=320*point[picnum][0]-point[picnum][2]; x<320*point[picnum][1]; x+=320)
                pixels[x] = Color.RED;
            for (int x=320*point[picnum][0]-point[picnum][3]; x<320*point[picnum][1]; x+=320)
                pixels[x] = Color.RED;
        }else{
            status.setText("Garbage Not Detected");
        }
        try {
            picture.setPixels(pixels, 0, picture.getWidth(), 0, 0, picture.getWidth(), picture.getHeight());
        }catch (Exception e){
            Log.d("Error", "onCreate: "+e);
        }
        im.setImageBitmap(picture);
        File pic_folder = new File(Environment.getExternalStorageDirectory(), "Pik-To-Clean");
        if (!pic_folder.exists()) {
            pic_folder.mkdirs();
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmmss");
        File photo=new File(pic_folder,"ptc_"+sdf.format(new Date())+".jpg");
        OutputStream out=null;
        try {
            out=new FileOutputStream(photo);
            picture.compress(Bitmap.CompressFormat.JPEG,100,out);
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
       /* FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
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
        });*/
    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
//                && data != null && data.getData() != null ){
//
//            filePath = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                imageView.setImageBitmap(bitmap);
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    private void uplodImageg() {
//
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Uploading...");
//        progressDialog.show();
//        StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
//        ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                progressDialog.dismiss();
//                Toast.makeText(classification.this, "Uploaded", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progressDialog.dismiss();
//                Toast.makeText(classification.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
//                        .getTotalByteCount());
//                progressDialog.setMessage("Uploaded "+(int)progress+"%");
//
//            }
//        });
//
//
//    }
}
