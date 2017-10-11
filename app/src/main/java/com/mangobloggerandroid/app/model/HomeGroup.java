package com.mangobloggerandroid.app.model;

import java.util.List;

/**
 * Created by ujjawal on 8/10/17.
 */

public class HomeGroup {

        private int mGravity;
        private String mText;
        private int mCardSize;
        private List<HomeItem> mHomeItems;

        public HomeGroup(int gravity, int cardSize, String text,  List<HomeItem> apps) {
            mGravity = gravity;
            mText = text;
            mCardSize = cardSize;
            mHomeItems = apps;
        }

        public String getText(){
            return mText;
        }

        public int getGravity(){
            return mGravity;
        }

    public int getCardSize() {
        return mCardSize;
    }

    public List<HomeItem> getApps(){
            return mHomeItems;
        }


}
