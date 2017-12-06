
package com.mangobloggerandroid.app.Splash;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mangobloggerandroid.app.R;
import com.mangobloggerandroid.app.activity.WelcomeActivity;

public class SplashActivity extends Activity {

    // Splash screen timer
    private FirebaseAnalytics mAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAnalytics = FirebaseAnalytics.getInstance(this);

        Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(i);

                finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAnalytics.setCurrentScreen(this, getClass().getSimpleName(), "Home Screen");
    }
}
