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
   // private DrawerLayout drawer;
    //private Toolbar toolbar;


    public static int navItemIndex = 0;

    private static final String TAG_CAM = "Camera";
    private static final String TAG_PROFILE = "Profile";
    private static final String TAG_CONTACT = "About";
    public static String CURRENT_TAG = TAG_PROFILE;
    private Bitmap bitmap=null;



//    private String[] activityTitles;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_bar);
     //   toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
        mHandler = new Handler();
       // drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
       // fab = (FloatingActionButton) findViewById(R.id.fab);


        //activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        loadNavHeader();
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_PROFILE;
            loadHomeFragment();
        }

    }
//    private void loadNavHeader() {
//        // name, website
//
//
//        // loading header background image
//
//
//        // showing dot next to notifications label
//        navigationView.getMenu().getItem(1).setActionView(R.layout.menu_dot);
//    }
    private void loadHomeFragment() {
        selectNavMenu();
    //    setToolbarTitle();

//        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
//            drawer.closeDrawers();
//
//            // show or hide the fab button
//            toggleFab();
//            return;
//        }
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
      //  toggleFab();
      //  drawer.closeDrawers();
      //  invalidateOptionsMenu();
    }
    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            case 1:
                  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
                CamFragment camFragment = new CamFragment(bitmap);
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

//    private void setToolbarTitle() {
//        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
//    }

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
//
//       // ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//                super.onDrawerClosed(drawerView);
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//                super.onDrawerOpened(drawerView);
//            }
//        };
    //    drawer.setDrawerListener(actionBarDrawerToggle);
    //    actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawers();
//            return;
//        }
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


//    private void toggleFab() {
//        if (navItemIndex == 0)
//            fab.show();
//        else
//            fab.hide();
//    }

}