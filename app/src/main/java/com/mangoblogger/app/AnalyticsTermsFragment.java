package com.mangoblogger.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;


import android.webkit.WebView;
import android.widget.ProgressBar;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by karthikprasad on 7/29/17.
 *
 */

@SuppressLint("SetJavaScriptEnabled")
public class AnalyticsTermsFragment extends Fragment {

    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_analytics_terms, container, false);

        WebView mWebView = (WebView) rootView.findViewById(R.id.webview);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress);

       mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                super.onProgressChanged(view, progress);
                if(mProgressBar != null) {
                    mProgressBar.setProgress(progress);
                    mProgressBar.setVisibility(progress == 100 ? GONE : VISIBLE);
                }
            }
        });
        mWebView.setWebViewClient(new MyWebViewClient(getActivity()));

        mWebView.loadUrl("https://www.mangoblogger.com/analytics-definitions/");

        //      This thing here no longer works
        // This used to load previous page on pressing back key
        mWebView.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    com.mangoblogger.app.widget.WebView webView = (com.mangoblogger.app.widget.WebView) v;

                    switch(keyCode)
                    {
                        case KeyEvent.KEYCODE_BACK:
                            if(webView.canGoBack())
                            {
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }

                return false;
            }
        });


        return rootView;
    }



}
