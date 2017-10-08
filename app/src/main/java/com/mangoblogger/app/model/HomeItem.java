package com.mangoblogger.app.model;

/**
 * Created by ujjawal on 8/10/17.
 */

public class HomeItem {
    private int mDrawable;
    private String mName;

    public HomeItem(String name, int drawable) {
        mName = name;
        mDrawable = drawable;
    }


    public int getDrawable() {
        return mDrawable;
    }

    public String getName() {
        return mName;
    }
}
