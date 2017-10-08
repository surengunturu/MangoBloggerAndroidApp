package com.mangoblogger.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


import com.mangoblogger.app.R;
import com.mangoblogger.app.fragment.FirebaseListFragment;
import com.mangoblogger.app.fragment.WebFragment;
import com.mangoblogger.app.model.HomeItem;

/**
 * Created by ujjawal on 9/10/17.
 *
 */

public class ItemActivity extends AppCompatActivity {
    public static final String ITEM = "item";

    private HomeItem mItem;

    public static Intent getStartIntent(Context context, HomeItem item) {
        Intent starter = new Intent(context, ItemActivity.class);
        starter.putExtra(ITEM, item);
        return starter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItem = getIntent().getParcelableExtra(ITEM);
        setContentView(R.layout.activity_item);
        attachFragment();
    }

    private void attachFragment() {
        FragmentManager fm = getSupportFragmentManager();
        if(!mItem.isWebView()) {
            fm.beginTransaction().replace(R.id.container,
                    FirebaseListFragment.newInstance(mItem.getUrl()), mItem.getName())
                    .commit();
        } else {
            fm.beginTransaction().replace(R.id.container,
                    WebFragment.newInstance(mItem.getUrl(), mItem.getName()), mItem.getName())
                    .commit();
        }

    }

}
