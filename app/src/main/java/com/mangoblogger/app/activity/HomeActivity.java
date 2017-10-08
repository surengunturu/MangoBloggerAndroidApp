package com.mangoblogger.app.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.mangoblogger.app.R;
import com.mangoblogger.app.adapter.HomeItemAdapter;
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
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.inflateMenu(R.menu.main);
//        toolbar.setOnMenuItemClickListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        setupAdapter();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void setupAdapter() {
        List<HomeItem> items = getApps();

        HomeItemAdapter itemAdapter = new HomeItemAdapter();
//            itemAdapter.addSnap(new HomeGroup(Gravity.CENTER_HORIZONTAL, "HomeGroup center", items));
            itemAdapter.addSnap(new HomeGroup(Gravity.START, HomeItemAdapter.CARD_SIZE_SMALL, "Explore Mangoblogger", items));
//            itemAdapter.addSnap(new HomeGroup(Gravity.END, "HomeGroup end", items));
            itemAdapter.addSnap(new HomeGroup(Gravity.CENTER, HomeItemAdapter.CARD_SIZE_MEDIUM, "Our Recent Blogs", items));
        mRecyclerView.setAdapter(itemAdapter);
    }

    private List<HomeItem> getApps() {
        List<HomeItem> apps = new ArrayList<>();
        apps.add(new HomeItem("Analytics Terms", R.mipmap.analytics_cover));
        apps.add(new HomeItem("Ux Terms", R.mipmap.uxterms_cover));
        apps.add(new HomeItem("Blogs", R.mipmap.blog_cover));

        return apps;
    }

}

