package com.mangoblogger.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by karthikprasad on 7/29/17.
 *
 */
@SuppressLint("SetJavaScriptEnabled")
public class AnalyticsTermsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_analytics_terms, container, false);

        WebView myWebView = (WebView) rootView.findViewById(R.id.webview);
        myWebView.setWebViewClient(new MyWebViewClient(getActivity()));
//       must be enabled to
        myWebView.getSettings().setJavaScriptEnabled(true);

        myWebView.loadUrl("https://www.mangoblogger.com/analytics-definitions/");

        return rootView;
    }


}
