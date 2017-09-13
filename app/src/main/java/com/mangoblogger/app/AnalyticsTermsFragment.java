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

        WebView myWebView = (WebView) rootView.findViewById(R.id.webview);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progress);

        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                super.onProgressChanged(view, progress);
                if(mProgressBar != null) {
                    mProgressBar.setProgress(progress);
                    mProgressBar.setVisibility(progress == 100 ? GONE : VISIBLE);
                }
            }
        });
        myWebView.setWebViewClient(new MyWebViewClient(getActivity()));
//       must be enabled to
        myWebView.getSettings().setJavaScriptEnabled(true);

        myWebView.loadUrl("https://www.mangoblogger.com/analytics-definitions/");

        // Quick fix to load previously opened url on back key pressed
        // To do - Create a separate class by extending android.webkit.webview
        //          and move this code into same.
        myWebView.setOnKeyListener(new View.OnKeyListener()
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
