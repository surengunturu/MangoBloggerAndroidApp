package com.mangobloggerandroid.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.mangobloggerandroid.app.Login.User;


/**
 * Created by ujjawal on 4/11/17.
 *
 */

public class PreferenceUtil {
    private static final String USER_PREFERENCES = "userPreferences";
    private static final String PREFERENCE_ID = USER_PREFERENCES + ".id";
    private static final String PREFERENCE_USERNAME = USER_PREFERENCES + ".username";
    private static final String PREFERENCE_EMAIL = USER_PREFERENCES + ".email";
    private static final String PREFERENCE_LOGGED_IN = USER_PREFERENCES + ".isLoggedIn";
    private static final String PREFERENCE_DISPLAY_NAME = USER_PREFERENCES + ".displayName";
    private static final String PREFERENCE_COOKIE = USER_PREFERENCES + ".cookie";
    private PreferenceUtil() {
        //no instance
    }


    public static void writeUserToPreferences(Context context, User user) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(PREFERENCE_ID, user.getId());
        editor.putString(PREFERENCE_USERNAME, user.getUsername());
        editor.putString(PREFERENCE_EMAIL, user.getEmail());
        editor.putBoolean(PREFERENCE_LOGGED_IN, user.isLoggedIn());
        editor.putString(PREFERENCE_DISPLAY_NAME, user.getDisplayname());
        editor.putString(PREFERENCE_COOKIE, user.getCookie());
        editor.apply();
    }


    public static User getUser(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        final String id = preferences.getString(PREFERENCE_ID, "007");
        final String username = preferences.getString(PREFERENCE_USERNAME, "Test User");
        final String email = preferences.getString(PREFERENCE_EMAIL, "user@example.com");
        final boolean isLoggedIn = preferences.getBoolean(PREFERENCE_LOGGED_IN, false);
        final String displayName = preferences.getString(PREFERENCE_DISPLAY_NAME, "Test User");
        final String cookie = preferences.getString(PREFERENCE_COOKIE, "null");

        return new User(id, username, email, displayName, isLoggedIn, cookie);
    }

    /**
     * Signs out a user by removing all it's data.
     *
     * @param context The context which to obtain the SharedPreferences from.
     */
    public static void signOut(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(PREFERENCE_LOGGED_IN, false);
        editor.apply();
    }

    /**
     * Checks whether a user is currently signed in.
     *
     * @param context The context to check this in.
     * @return <code>true</code> if login data exists, else <code>false</code>.
     */
    public static boolean isSignedIn(Context context) {
        final SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getBoolean(PREFERENCE_LOGGED_IN, false);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }
}
