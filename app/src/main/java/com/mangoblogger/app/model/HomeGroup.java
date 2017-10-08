package com.mangoblogger.app.model;

import java.util.List;

/**
 * Created by ujjawal on 8/10/17.
 */

public class HomeGroup {

        private int mGravity;
        private String mText;
        private int cardSize;
        private List<HomeItem> mHomeItems;

        public HomeGroup(int gravity, int cardSize, String text,  List<HomeItem> apps) {
            mGravity = gravity;
            mText = text;
            mHomeItems = apps;
        }

        public String getText(){
            return mText;
        }

        public int getGravity(){
            return mGravity;
        }

    public int getCardSize() {
        return cardSize;
    }

    public List<HomeItem> getApps(){
            return mHomeItems;
        }


}
