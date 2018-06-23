package com.mangobloggerandroid.app;

import android.app.Application;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tagmanager.Container;
import com.google.android.gms.tagmanager.ContainerHolder;
import com.google.android.gms.tagmanager.TagManager;

import java.util.concurrent.TimeUnit;

public class MangoBlogger extends Application {

    private static final String CONTAINER_ID = "GTM-N765QMH";
    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);


//        initGoogleAnalytics();
//        initGoogleTagManager();
    }

    public Tracker mTracker;

    public ContainerHolder mContainerHolder;
    public TagManager mTagManager;

    // Get the Tag Manager
    public TagManager getTagManager() {
        if (mTagManager == null) {
            // create the TagManager, save it in mTagManager
            mTagManager = TagManager.getInstance(this);
        }
        return mTagManager;
    }

    // Set the ContainerHolder
    public void setContainerHolder(ContainerHolder containerHolder) {
        mContainerHolder = containerHolder;
    }

    // Get the ContainerHolder
    public ContainerHolder getContainerHolder() {
        return mContainerHolder;
    }

}
