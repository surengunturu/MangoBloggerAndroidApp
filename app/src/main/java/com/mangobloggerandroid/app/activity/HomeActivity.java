package com.mangobloggerandroid.app.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.mangobloggerandroid.app.BuildConfig;
import com.mangobloggerandroid.app.Login.LoginActivity;
import com.mangobloggerandroid.app.MangoBlogger;
import com.mangobloggerandroid.app.PreferenceUtil;
import com.mangobloggerandroid.app.R;
import com.mangobloggerandroid.app.fragment.AboutFragment;
import com.mangobloggerandroid.app.fragment.BookmarkedFragment;
import com.mangobloggerandroid.app.fragment.HomeFragment;
import com.mangobloggerandroid.app.util.AppUtils;

import io.fabric.sdk.android.Fabric;

/**
 * Created by ujjawal on 8/10/17.
 *
 */

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String ABOUT_KEY = "about";
    public static final String CONTACT_NUMBER_KEY = "contact_number"; // value must be equal to parameter in firebase remote config
    public static final String COUNTRY_CODE_KEY = "contact_number_country_code"; // value must be equal to parameter in firebase remote config
    public static final String ADDRESS_KEY = "address";
    public static final String GEO_LATITUDE_KEY = "geo_latitude";
    public static final String GEO_LONGITUDE_KEY = "geo_longitude";

    private static final int ANIM_DURATION_TOOLBAR = 300;


    private Toolbar mToolbar;
    private ImageView mToolbarLogo;
    private MenuItem mNotificationMenuItem;


    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    private String mCountryCode;
    private String mContactNumber;
    private String mAbout;
    private String mAddress;
    private String mGeoLatitude;
    private String mGeoLongitude;

//    private boolean doubleBackToExitPressedOnce = false;
    private boolean pendingIntroAnimation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   attachFragment(HomeFragment.newInstance(), false);
                    return true;
                case R.id.navigation_dashboard:
                    attachFragment(BookmarkedFragment.newInstance(), true);
                    return true;
                case R.id.navigation_notifications:
                    attachFragment(AboutFragment.newInstance(mAbout, mCountryCode, mContactNumber, mAddress,
                            mGeoLatitude, mGeoLongitude), true);
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            pendingIntroAnimation = true;
        }

        Fabric.with(this, new Crashlytics());
        Firebase.setAndroidContext(this);

        // Obtain the FirebaseAnalytics instance.
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setMinimumSessionDuration(20000);
        // firebase remote configuration
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfigSettings();
        fetchFirebaseRemoteConfig();

        Bundle bundle = new Bundle();
        String id = "MangoBlogger";
        String name = "Google analytics";
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        BottomNavigationView mNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarLogo = (ImageView) findViewById(R.id.app_logo);
        setupToolbar();
        setUpDrawer();



        attachFragment(HomeFragment.newInstance(), false);

        //google analytics
        ((MangoBlogger)getApplication()).startTracking();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /*@Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }*/

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() < 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        mNotificationMenuItem = menu.findItem(R.id.action_notification);
        mNotificationMenuItem.setActionView(R.layout.menu_item_view);

//        inboxMenuItem.setActionView(R.layout.menu_item_view);
        if (pendingIntroAnimation) {
            pendingIntroAnimation = false;
            startIntroAnimation();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_notification:

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void setupToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    private void setUpDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mToolbar.getNavigationIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_signOut) {
            PreferenceUtil.signOut(this);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void attachFragment(Fragment fragment, boolean addTobackStack) {
        FragmentManager fm = getSupportFragmentManager();
        if(addTobackStack) {
            fm.beginTransaction().replace(R.id.container,
                    fragment).addToBackStack(null)
                    .commit();
        } else {
            fm.beginTransaction().replace(R.id.container,
                    fragment)
                    .commit();
        }


    }

    private void firebaseRemoteConfigSettings() {
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

    }

    private void fetchFirebaseRemoteConfig() {
        long cacheExpiration = 36008*12; // 12 hour in seconds.
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();

                        } /*else {
                            Toast.makeText(MainActivity.this, "Fetch Failed",
                                    Toast.LENGTH_SHORT).show();
                        }*/ // comment it out to test
                        getRemoteConfigs();
                    }
                });
    }

    private void getRemoteConfigs() {
        mAbout = mFirebaseRemoteConfig.getString(ABOUT_KEY);
        mCountryCode = mFirebaseRemoteConfig.getString(COUNTRY_CODE_KEY);
        mContactNumber = mFirebaseRemoteConfig.getString(CONTACT_NUMBER_KEY);
        mAddress = mFirebaseRemoteConfig.getString(ADDRESS_KEY);
        mGeoLatitude = mFirebaseRemoteConfig.getString(GEO_LATITUDE_KEY);
        mGeoLongitude = mFirebaseRemoteConfig.getString(GEO_LONGITUDE_KEY);
    }

    private void startIntroAnimation() {
//        fabCreate.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
        if(mNotificationMenuItem != null && mToolbar != null && mToolbarLogo != null) {
        int actionbarSize = AppUtils.dpToPx(56);
        mToolbar.setTranslationY(-actionbarSize);
        mToolbarLogo.setTranslationY(-actionbarSize);

            mNotificationMenuItem.getActionView().setTranslationY(-actionbarSize);

            mToolbar.animate()
                    .translationY(0)
                    .setDuration(ANIM_DURATION_TOOLBAR)
                    .setStartDelay(300);
            mToolbarLogo.animate()
                    .translationY(0)
                    .setDuration(ANIM_DURATION_TOOLBAR)
                    .setStartDelay(400);
            mNotificationMenuItem.getActionView().animate()
                    .translationY(0)
                    .setDuration(ANIM_DURATION_TOOLBAR)
                    .setStartDelay(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                        }
                    })
                    .start();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}

