package piktoclean.com.pik_to_clean;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

   // private int STORAGE_PERMISSION_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            //try{
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                        if(pref.getBoolean("activity_executed", false)){
                            SharedPreferences mpreference = getSharedPreferences("piktoclean.com.pik_to_clean", Context.MODE_PRIVATE);
                            String name= mpreference.getString("user","");
                            if(name!="") {
                                Intent intent = new Intent(MainActivity.this, NavBarActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }
                        } else {
                            SharedPreferences.Editor ed = pref.edit();
                            ed.putString("user","null");
                            ed.putBoolean("activity_executed", true);
                            ed.commit();
                            Intent i = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                }, SPLASH_TIME_OUT);
//            } catch (Exception e) {
//                Intent i = new Intent(MainActivity.this, CamActivity.class);
//                startActivity(i);
//                finish();
//            }
    }
}
