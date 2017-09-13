package com.mangoblogger.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import com.google.firebase.analytics.FirebaseAnalytics;


public class MainActivity extends AppCompatActivity {
    private CoordinatorLayout mCoordinatorLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //inflate analytics fragment
                    changeFragment(0);
                    return true;
                case R.id.navigation_dashboard:
                    //inflate ux fragment
                    changeFragment(1);
                    return true;
                case R.id.navigation_notifications:
                    //inflate about fragment
                    changeFragment(2);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.content_frame);

        // Obtain the FirebaseAnalytics instance.
       FirebaseAnalytics  firebaseAnalytics = FirebaseAnalytics.getInstance(this);
       firebaseAnalytics.setAnalyticsCollectionEnabled(true);
        firebaseAnalytics.setMinimumSessionDuration(20000);

       Bundle bundle = new Bundle();
        String id = "MangoBlogger";
        String name = "Google analytics";
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        changeFragment(0);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * To load fragments into container
     *
     * @param position menu index
     */
    private void changeFragment(int position) {

        Fragment newFragment;
        if (!AppUtils.hasConnection(this)) {
            Snackbar.make(mCoordinatorLayout, R.string.offline_notice, Snackbar.LENGTH_LONG).show();
        }

        if (position == 0) {
            newFragment = new AnalyticsTermsFragment();
        } else if (position == 1) {
            newFragment = new UxTermsFragment();
        } else {
            newFragment = new AboutFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(
                R.id.container, newFragment)
                .commit();
    }

}
