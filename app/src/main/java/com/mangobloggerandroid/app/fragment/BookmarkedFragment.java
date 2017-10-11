package com.mangobloggerandroid.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mangobloggerandroid.app.R;

/**
 * Created by ujjawal on 11/10/17.
 */

public class BookmarkedFragment extends Fragment {
    public BookmarkedFragment() {
        // Required empty public constructor
    }

    public static BookmarkedFragment newInstance() {
        BookmarkedFragment fragment = new BookmarkedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //args
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bookmarked, container, false);
        return v;
    }


}
