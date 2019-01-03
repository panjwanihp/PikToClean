package piktoclean.com.pik_to_clean;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import piktoclean.com.pik_to_clean.fragment.CamFragment;
import piktoclean.com.pik_to_clean.fragment.ContactFragment;
import piktoclean.com.pik_to_clean.fragment.ProfileFragment;

public class NavBarActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;



    public static int navItemIndex = 0;

    private static final String TAG_CAM = "Camera";
    private static final String TAG_PROFILE = "Profile";
    private static final String TAG_CONTACT = "About";
    public static String CURRENT_TAG = TAG_PROFILE;
    private Bitmap bitmap=null;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);
        mHandler = new Handler();
       navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_PROFILE;
            loadHomeFragment();
        }

    }
    private void loadHomeFragment() {
        selectNavMenu();
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                if(fragment!=null){
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.fragment_container, fragment, CURRENT_TAG);
                    fragmentTransaction.commitAllowingStateLoss();
                }
            }
        };
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
    }
    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            case 1:
                CamFragment camFragment = new CamFragment();
                return camFragment;

            case 2:
                ContactFragment contactFragment = new ContactFragment();
                return contactFragment;

            default:
                return new ProfileFragment();
        }

    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
         try {
             bitmap = (Bitmap) data.getExtras().get("data");
         }catch (Exception e){

             bitmap=null;
         }
    }


    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;

                    case R.id.nav_cam:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_CAM;
                        break;
                    case R.id.nav_profile:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_PROFILE;
                        break;
                    case R.id.nav_contact:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_CONTACT;
                        break;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_PROFILE;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }


}