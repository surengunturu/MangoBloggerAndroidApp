package com.mangobloggerandroid.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ujjawal on 8/10/17.
 *
 */

public class HomeItem implements Parcelable {
    private int mDrawable;
    private String mName;
    private String mExtra;
    private String mUrl;
    private boolean mWebView;

   

    

    public HomeItem(String name,  String url, String extra, int drawable, boolean webView) {
        mExtra = extra;
        mUrl = url;
        mName = name;
        mDrawable = drawable;
        mWebView = webView;
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

    public boolean isWebView() {
        return mWebView;
    }
    
//    parcel part
    public HomeItem(Parcel in) {
        mName = in.readString();
        mUrl = in.readString();
        mExtra = in.readString();
        mDrawable = in.readInt();
        int webValue = in.readInt();
        if(webValue == 1) {
            mWebView = true;
        } else {
            mWebView = false;
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mUrl);
        parcel.writeString(mExtra);
        parcel.writeInt(mDrawable);
        if(mWebView) {
            parcel.writeInt(1);
        } else {
            parcel.writeInt(0);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HomeItem> CREATOR = new Parcelable.Creator<HomeItem>() {
        @Override
        public HomeItem createFromParcel(Parcel in) {
            return new HomeItem(in);
        }

        @Override
        public HomeItem[] newArray(int size) { return new HomeItem[size]; }

    };
}
