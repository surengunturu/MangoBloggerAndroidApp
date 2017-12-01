package com.mangobloggerandroid.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mangobloggerandroid.app.R;
import com.mangobloggerandroid.app.interfaces.FragmentDataPasser;

/**
 * Created by ujjawal on 11/10/17.
 */

public class BookmarkedFragment extends Fragment {
    private FragmentDataPasser mDataPasser;
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
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(getContext());
        analytics.setCurrentScreen(getActivity(), getClass().getSimpleName(), getClass().getSimpleName());

        if (getArguments() != null) {
            //args
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (activity instanceof FragmentDataPasser) {
            mDataPasser = (FragmentDataPasser) context;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        passData();
    }

    private void passData() {
        mDataPasser.onDataPass(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bookmarked, container, false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(getContext());
        analytics.setCurrentScreen(getActivity(), getClass().getSimpleName(), "Bookmark Screen");

    }
}
