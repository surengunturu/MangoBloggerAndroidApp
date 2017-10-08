package com.mangoblogger.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;

import com.mangoblogger.app.R;
import com.mangoblogger.app.adapter.HomeBaseAdapter;
import com.mangoblogger.app.model.HomeGroup;
import com.mangoblogger.app.model.HomeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ujjawal on 8/10/17.
 *
 */

public class HomeActivity extends AppCompatActivity  {


    private RecyclerView mRecyclerView;
    private MenuItem mPrevMenuItem;
    private BottomNavigationView mNavigation;
    private boolean mHorizontal;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //inflate analytics fragment
//                    mViewPager.setCurrentItem(0);
//                    initSnackBar();

                    return true;
                case R.id.navigation_dashboard:
                    //inflate ux fragment
//                    mViewPager.setCurrentItem(1);
//                    initSnackBar();
                    return true;
                case R.id.navigation_notifications:
                    //inflate about fragment
//                    mViewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mNavigation = (BottomNavigationView) findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        setupAdapter();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void setupAdapter() {
        HomeBaseAdapter itemAdapter = new HomeBaseAdapter(this);

        itemAdapter.addSnap(new HomeGroup(Gravity.START, HomeBaseAdapter.CARD_SIZE_SMALL, "Explore Mangoblogger", getExploreItems()));
        itemAdapter.addSnap(new HomeGroup(Gravity.START, HomeBaseAdapter.CARD_SIZE_MEDIUM, "Our Recent Blogs", getRecentBlogs()));
        itemAdapter.addSnap(new HomeGroup(Gravity.CENTER, HomeBaseAdapter.CARD_SIZE_PAGER, "Our Services", getServices()));

        mRecyclerView.setAdapter(itemAdapter);


    }

    private List<HomeItem> getExploreItems() {
        List<HomeItem> exploreItems = new ArrayList<>();
        exploreItems.add(new HomeItem("Analytics Terms",
                "https://mangoblogger-9ffff.firebaseio.com/analytics", "null",
                R.mipmap.analytics_cover, false));
        exploreItems.add(new HomeItem("Ux Terms",
                "https://mangoblogger-9ffff.firebaseio.com/ux_terms", "null",
                R.mipmap.uxterms_cover, false));
        exploreItems.add(new HomeItem("Blogs",
                "https://www.mangoblogger.com/blog/", "null",
                R.mipmap.blog_cover, true));

        return exploreItems;
    }

    private List<HomeItem> getRecentBlogs() {
        List<HomeItem> blogs = new ArrayList<>();
        blogs.add(new HomeItem("Indian Mobile Congress 2017",
                "https://www.mangoblogger.com/blog/highlights-of-india-mobile-congress-2017/",
                "By : Yatin", R.mipmap.recent_blog_one_cover, true));
        blogs.add(new HomeItem("Add Social Login In WordPress Site",
                "https://www.mangoblogger.com/blog/wordpress-plugin-installation/",
                "By : Yatin", R.mipmap.recent_blog_two_cover, true));
        blogs.add(new HomeItem("Guide : Google Tag Manager Installation",
                "https://www.mangoblogger.com/blog/google-tag-manager-installation-website/",
                "By : Yatin", R.mipmap.recent_blog_three_cover, true));
        blogs.add(new HomeItem("What is Google Analytics",
                "https://www.mangoblogger.com/blog/what-is-google-analytics/",
                "By : Siddhant", R.mipmap.recent_blog_four_cover, true));
        blogs.add(new HomeItem("All About Pixel Tracking",
                "https://www.mangoblogger.com/blog/all-about-tracking-pixel/",
                "By : Mangoblogger", R.mipmap.recent_blog_five_cover, true));
        return blogs;
    }

    private List<HomeItem> getServices()  {
        List<HomeItem> services = new ArrayList<>();
        services.add(new HomeItem("Analytics",
                "https://www.mangoblogger.com/product/google-analytics-dashboard/",
                "$100", R.mipmap.service_analytics_cover, true));
        services.add(new HomeItem("Kickstarter Package",
                "https://www.mangoblogger.com/product/google-analytics-and-google-tag-manager/",
                "$200", R.mipmap.service_kickstarter_cover, true));
        services.add(new HomeItem("SEO Consultation",
                "https://www.mangoblogger.com/product/seo-consultation/",
                "$200", R.mipmap.service_seo_cover, true));
        services.add(new HomeItem("Ux Consultation",
                "https://www.mangoblogger.com/product/ux-consultation/",
                "$500", R.mipmap.service_ux_consulation_cover, true));

        return services;
    }

}

