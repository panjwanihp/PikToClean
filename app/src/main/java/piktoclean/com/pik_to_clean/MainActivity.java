package piktoclean.com.pik_to_clean;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
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
                            Intent intent = new Intent(MainActivity.this, CamActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            SharedPreferences.Editor ed = pref.edit();
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
