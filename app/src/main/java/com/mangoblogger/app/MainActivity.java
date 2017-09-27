package com.mangoblogger.app;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;


import com.crashlytics.android.Crashlytics;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String ANALYTICS_URL_KEY = "analytics_url"; // value must be equal to parameter in firebase remote config
    private static final String UXTERMS_URL_KEY = "uxterms_url"; // value must be equal to parameter in firebase remote config
    public static final String ABOUT_KEY = "about";
    public static final String CONTACT_NUMBER_KEY = "contact_number"; // value must be equal to parameter in firebase remote config
    public static final String COUNTRY_CODE_KEY = "contact_number_country_code"; // value must be equal to parameter in firebase remote config
    public static final String ADDRESS_KEY = "address";
    public static final String GEO_LATITUDE_KEY = "geo_latitude";
    public static final String GEO_LONGITUDE_KEY = "geo_longitude";



    private CoordinatorLayout mCoordinator;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private MenuItem mPrevMenuItem;
    private BottomNavigationView mNavigation;
    private boolean doubleBackToExitPressedOnce = false;
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private String mCountryCode;
    private String mContactNumber;
    private String mAnalyticsUrl;
    private String mUxtermsUrl;
    private String mAbout;
    private String mAddress;
    private String mGeoLatitude;
    private String mGeoLongitude;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //inflate analytics fragment
                    mViewPager.setCurrentItem(0);
                    setAnalyticsScreenName("Analytics",
                            WebFragment.class.getName());

                    initSnackBar();

                    return true;
                case R.id.navigation_dashboard:
                    //inflate ux fragment
                    mViewPager.setCurrentItem(1);
                    setAnalyticsScreenName("Ux Terms",
                            WebFragment.class.getName());

                    initSnackBar();
                    return true;
                case R.id.navigation_notifications:
                    //inflate about fragment
                    mViewPager.setCurrentItem(2);
                    setAnalyticsScreenName("About",
                            AboutFragment.class.getName());
                    return true;
                case R.id.navigation_firebaseList :
                    mViewPager.setCurrentItem(3);
                    setAnalyticsScreenName("Firebase List", FirebaseList.class.getName());
                    return true;

            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        initViewPager();



        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
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



        mNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mCoordinator = (CoordinatorLayout) findViewById(R.id.content_frame);

        //google analytics
        ((SubApplication)getApplication()).startTracking();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void setAnalyticsScreenName(String screeName, String className) {
        mFirebaseAnalytics.setCurrentScreen(getParent(), screeName,
                className);
    }

    private void firebaseRemoteConfigSettings() {
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);

    }

    private void fetchFirebaseRemoteConfig() {
        long cacheExpiration = 3600; // 1 hour in seconds.
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

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(2);
    }

    private void initPagerAdapter() {
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        // shows list of contacts, populated from json file
        mPagerAdapter.addFragment(WebFragment.newInstance(mAnalyticsUrl, "Analytics"), "Analytics");
        // shows list of sent messages, populates from sqlite database
        mPagerAdapter.addFragment(WebFragment.newInstance(mUxtermsUrl, "Ux Terms"), "Ux Terms");
        mPagerAdapter.addFragment(AboutFragment.newInstance(mAbout, mCountryCode, mContactNumber, mAddress,
                mGeoLatitude, mGeoLongitude), "About");
       mPagerAdapter.addFragment(FirebaseList.newInstance(), "Firebase List");

        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mPrevMenuItem != null) {
                    mPrevMenuItem.setChecked(false);
                }
                else
                {
                    mNavigation.getMenu().getItem(0).setChecked(false);
                }
                mNavigation.getMenu().getItem(position).setChecked(true);
                mPrevMenuItem = mNavigation.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getRemoteConfigs() {
        mAnalyticsUrl = mFirebaseRemoteConfig.getString(ANALYTICS_URL_KEY);
        mUxtermsUrl = mFirebaseRemoteConfig.getString(UXTERMS_URL_KEY);
        mAbout = mFirebaseRemoteConfig.getString(ABOUT_KEY);
        mCountryCode = mFirebaseRemoteConfig.getString(COUNTRY_CODE_KEY);
        mContactNumber = mFirebaseRemoteConfig.getString(CONTACT_NUMBER_KEY);
        mAddress = mFirebaseRemoteConfig.getString(ADDRESS_KEY);
        mGeoLatitude = mFirebaseRemoteConfig.getString(GEO_LATITUDE_KEY);
        mGeoLongitude = mFirebaseRemoteConfig.getString(GEO_LONGITUDE_KEY);
        initPagerAdapter();
    }



    private void initSnackBar() {
        if (!AppUtils.hasConnection(this)) {
            Snackbar.make(mCoordinator, R.string.offline_notice, Snackbar.LENGTH_LONG).show();
        }
    }



    /**
     * class used to populate fragments in viewpager extends FragmentPagerAdapter
     */
    private static class PagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        private PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        /**
         * Call to add new fragment in the view pager
         * @param fragment the fragment you want to add
         * @param title title of fragment
         */
        private void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }


        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }

}
