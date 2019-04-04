package piktoclean.com.pik_to_clean;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;
import com.google.firebase.storage.FirebaseStorage;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class classification extends AppCompatActivity {
    private ImageView im;
    FirebaseFirestore db;
    private Button proceedbtn;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseFirestore firebaseFirestore  = FirebaseFirestore.getInstance();
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
    private LinearLayout login_enable,login_disable;
    private Context c;
    private Button btn;
    private double lat,lon;
    private String Dname,Dphone;
    private GeoPoint gp;
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
        db = FirebaseFirestore.getInstance();
        CollectionReference GTdriver = db.collection("GTdriver");
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
        lat=Double.valueOf(i.getStringExtra("latitude"));
        lon=Double.valueOf(i.getStringExtra("longitude"));
        gp=new GeoPoint(lat,lon);
        GTdriver.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot d:task.getResult()){


                        float[] result = new float[2];
                        Location.distanceBetween(lat, lon,
                                d.getGeoPoint("Spoint").getLatitude(), d.getGeoPoint("Spoint").getLongitude(), result);
                        if(result[0]<1000){
                            Dname=  d.getString("Dname");
                            Dphone=  d.getString("Dphone");
                            break;
                        }

                    }
                }
            }
        });
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
        final String picname= "ptc_"+sdf.format(new Date())+".jpg";
        final File photo=new File(pic_folder,picname);
        OutputStream out=null;
        try {
            out=new FileOutputStream(photo);
            picture.compress(Bitmap.CompressFormat.JPEG,100,out);
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        proceedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                SharedPreferences mpreference = getSharedPreferences("piktoclean.com.pik_to_clean", Context.MODE_PRIVATE);
                final String phno= mpreference.getString("user","");
                final String name= mpreference.getString("name","");
                if(phno!="") {
                    if(flag==0){
                        try {
                            storage = FirebaseStorage.getInstance();
                            storageReference = storage.getReference();
                            filePath = Uri.fromFile(photo);
                            if(filePath != null)
                            {
                                final ProgressDialog progressDialog = new ProgressDialog(classification.this);
                                progressDialog.setTitle("Uploading...");
                                progressDialog.show();

                                StorageReference ref = storageReference.child("images/"+ picname);
                                ref.putFile(filePath)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {


                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Map<String, Object> info = new HashMap<>();
                                                info.put("Dname", Dname);
                                                info.put("Dphone", Dphone);
                                                info.put("Location", gp);
                                                info.put("Uname", name);
                                                info.put("Uphone", phno);
                                                db.collection("Garbage").document(picname)
                                                        .set(info)
                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d("done", "DocumentSnapshot successfully written!");
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Log.w("donee", "Error writing document", e);
                                                            }
                                                        });


                                                progressDialog.dismiss();
                                                Toast.makeText(classification.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                                Intent i=new Intent(classification.this,NavBarActivity.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(classification.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                                        .getTotalByteCount());
                                                progressDialog.setMessage("Uploaded "+(int)progress+"%");
                                            }
                                        });
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }else{
                        Intent i=new Intent(classification.this,NavBarActivity.class);
                        startActivity(i);
                        finish();
                    }
                }else{
                    Toast.makeText(classification.this,"User Is Logged In",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(classification.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        });
    }

}
