package com.mangoblogger.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by karthikprasad on 7/29/17.
 */

public class UxTermsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ux_terms, container, false);

        WebView myWebView = (WebView) rootView.findViewById(R.id.webview);
        myWebView.setWebViewClient(new MyWebViewClient(getActivity()));

       /* myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                getActivity().setProgress(progress * 1000);
            }
        });*/
        myWebView.getSettings().setJavaScriptEnabled(true);


        myWebView.loadUrl("https://www.mangoblogger.com/ux-definitions/");

        return rootView;
    }
}
