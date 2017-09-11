package com.mangoblogger.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by karthikprasad on 7/29/17.
 */

public class MyWebViewClient extends WebViewClient {

    private Context context;

    public MyWebViewClient(Context context) {
        this.context = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
       if (Uri.parse(url).getHost().equals("www.mangoblogger.com")) {
            // This is my web site, so do not override; let my WebView load the page
            return false;
        }
        // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
        return true;
    }


// Here you can use javascript to target the html tags and do whatever you want to
//    javascript must be enabled in child view to make it work
    @Override
    public void onPageCommitVisible(WebView view, String url) {
        super.onPageCommitVisible(view, url);
        view.loadUrl("javascript:document.querySelector('header').setAttribute('style','display:none;');");
        view.loadUrl("javascript:document.querySelector('footer').setAttribute('style','display:none;');");
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        Toast.makeText(context, "Oh no! " + description, Toast.LENGTH_SHORT).show();
    }

    /**
     * A custom onBackPressed method which loads previously opened url in same webview on
     * back key pressed.
     * @param myWebView the current webview
     */
    public void onBackPressed(WebView myWebView) {
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
    }
}
