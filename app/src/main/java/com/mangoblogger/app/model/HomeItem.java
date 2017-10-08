package com.mangoblogger.app.model;

/**
 * Created by ujjawal on 8/10/17.
 */

public class HomeItem {
    private int mDrawable;
    private String mName;
    private String mExtra;
    private String mUrl;

    public HomeItem(String name, int drawable) {
        mName = name;
        mDrawable = drawable;
    }

    public HomeItem(String name,  String url, String author, int drawable) {
        new HomeItem(name, drawable);
        mExtra = author;
        mUrl = url;
    }


    public int getDrawable() {
        return mDrawable;
    }

    public String getName() {
        return mName;
    }

    public String getExtra() {
        return mExtra;
    }

    public String getUrl() {
        return mUrl;
    }
}
