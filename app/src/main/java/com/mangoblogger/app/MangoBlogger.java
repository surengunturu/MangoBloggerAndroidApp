package com.mangoblogger.app;

import android.app.Application;
import com.firebase.client.Firebase;

public class MangoBlogger extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
