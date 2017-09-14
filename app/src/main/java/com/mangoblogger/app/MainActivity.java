package com.mangoblogger.app;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String ANALYTICS_URL = "https://www.mangoblogger.com/analytics-definitions/";
    private static final String UXTERM_URL = "https://www.mangoblogger.com/ux-definitions/";

    private ViewPager mViewPager;
    private MenuItem mPrevMenuItem;
    private BottomNavigationView mNavigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //inflate analytics fragment
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    //inflate ux fragment
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    //inflate about fragment
                    mViewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewPager();

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



        mNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setOffscreenPageLimit(2);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        // shows list of contacts, populated from json file
        pagerAdapter.addFragment(WebFragment.newInstance(ANALYTICS_URL), "Analytics");
        // shows list of sent messages, populates from sqlite database
        pagerAdapter.addFragment(WebFragment.newInstance(UXTERM_URL), "Ux Terms");
        pagerAdapter.addFragment(new AboutFragment(), "About");

        mViewPager.setAdapter(pagerAdapter);

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
