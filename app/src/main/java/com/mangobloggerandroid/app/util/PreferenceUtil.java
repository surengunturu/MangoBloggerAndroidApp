package com.mangobloggerandroid.app.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mangobloggerandroid.app.Login.User;
import com.mangobloggerandroid.app.model.BlogModel;
import com.mangobloggerandroid.app.model.HomeItem;
import com.mangobloggerandroid.app.util.AppUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ujjawal on 4/11/17.
 *
 */

public class PreferenceUtil {
    private static final String USER_PREFERENCES = "userPreferences";
    private static final String PREFERENCE_ID = USER_PREFERENCES + ".id";
    private static final String PREFERENCE_USERNAME = USER_PREFERENCES + ".username";
    private static final String PREFERENCE_EMAIL = USER_PREFERENCES + ".email";
    public static final String PREFERENCE_LOGGED_IN = USER_PREFERENCES + ".isLoggedIn";
    private static final String PREFERENCE_DISPLAY_NAME = USER_PREFERENCES + ".displayName";
    private static final String PREFERENCE_COOKIE = USER_PREFERENCES + ".cookie";
    public static final String PREFERENCE_BLOG_LIST = ".blogLists";
    public static final String BLOG_LIST_SYNC_DATE = "blog_list_sync_date";
    public static final String UX_TERMS_LIST = "Ux_tems_list";
    public static final String ANALYTICS_TERMS_LIST = "analytics_terms_list";
    public static final String UX_TERMS_LIST_SYNC_DATE = "ux_terms_sync_date";
    public static final String ANALYTICS_TERMS_LIST_SYNC_DATE = "analytics_terms_sync_date";
    public static final String PREFERENCE_ERROR = "no_value_found";
    private PreferenceUtil() {
        //no instance
    }

    public static void writeDataString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(key, value);
        editor.apply();
    }

    public static void writeDataBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void writeTerms(Context context, List<BlogModel> termList, boolean isUxTerms) {
        Gson gson = new Gson();
        String json = gson.toJson(termList);
        writeDataString(context, isUxTerms?UX_TERMS_LIST:ANALYTICS_TERMS_LIST, json);
        writeDataString(context, isUxTerms?UX_TERMS_LIST_SYNC_DATE:ANALYTICS_TERMS_LIST_SYNC_DATE, AppUtils.getCurrentWeek());
    }

    public static List<BlogModel> getTerms(Context context, boolean isUxTerms) {
        Gson gson = new Gson();
        List<BlogModel> productFromShared = new ArrayList<>();
        String jsonPreferences = getStringData(context, isUxTerms?UX_TERMS_LIST:ANALYTICS_TERMS_LIST);

        Type type = new TypeToken<List<BlogModel>>() {}.getType();
        productFromShared = gson.fromJson(jsonPreferences, type);

        return productFromShared;
    }

    public static boolean isTermsSynced(Context context, boolean isUxTerms) {
        return getSharedPreferences(context).getString(isUxTerms?UX_TERMS_LIST_SYNC_DATE:ANALYTICS_TERMS_LIST_SYNC_DATE, PREFERENCE_ERROR)
                .equals(AppUtils.getCurrentWeek());
    }

    public static boolean isDataSynced(Context context) {
        return getSharedPreferences(context).getString(BLOG_LIST_SYNC_DATE, PREFERENCE_ERROR)
                .equals(AppUtils.getCurrentDate());
    }

    public static String getStringData(Context context, String key) {
        SharedPreferences preferences = getSharedPreferences(context);
        return  preferences.getString(key, PREFERENCE_ERROR);
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
