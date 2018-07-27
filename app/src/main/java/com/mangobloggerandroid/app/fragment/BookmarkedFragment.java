package com.mangobloggerandroid.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.analytics.FirebaseAnalytics;
import com.mangobloggerandroid.app.R;
import com.mangobloggerandroid.app.interfaces.FragmentDataPasser;

/**
 * Created by ujjawal on 11/10/17.
 */

public class BookmarkedFragment extends Fragment implements View.OnClickListener {
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.sendEmail).setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendEmail:
                startEmailIntent();
                break;
        }
    }

    private void startEmailIntent() {
        Log.i("Send email", "");
        Intent intent = new Intent(Intent.ACTION_SEND);
        if(intent.resolveActivity(getContext().getPackageManager()) != null) {
            getActivity().getIntent().setData(Uri.parse(("mailto: ")));
            String[] to = {""};
            String[] cc = {""};
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_EMAIL, to);
            intent.putExtra(Intent.EXTRA_CC, cc);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Mangoblogger Employee Onboarding App Link");
            String link_val = "https://chrome.google.com/webstore/detail/employee-onboarding-app/hmiefidoonaajllkjjhnhonkbcieincl?utm_source=app";
            String body = "<a href=\"" + link_val + "\">" + link_val+ "</a>";
            intent.putExtra(Intent.EXTRA_HTML_TEXT, Html.fromHtml(body));
            /*intent.setType("message/rfc822");   TESTING*/
            startActivity(Intent.createChooser(intent, "Send Email"));
            Log.i("Finished sending email", "");
        }
    }


}
