package com.mangoblogger.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
public class UxTermsFragment extends Fragment {
    private static final String URL = "https://www.mangoblogger.com/ux-definitions/";

    private WebView mWebView;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ux_terms, container, false);

        mWebView = (WebView) rootView.findViewById(R.id.webview);

        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        mWebView.setWebViewClient(new MyWebViewClient(getActivity()));
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(URL);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.loadUrl(mWebView.getUrl());
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                super.onProgressChanged(view, progress);
                if(mProgressBar != null) {
                    mProgressBar.setProgress(progress);
                    mProgressBar.setVisibility(progress == 100 ? GONE : VISIBLE);
                }
                if(mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(!(progress == 100));
                }
            }
        });

        //      This thing here no longer works
        // This used to load previous page on pressing back key
        mWebView.setOnKeyListener(new View.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    WebView webView = (WebView) v;

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
