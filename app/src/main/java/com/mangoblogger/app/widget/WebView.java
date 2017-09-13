package com.mangoblogger.app.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.mangoblogger.app.AppUtils;

import java.io.File;

/**
 * Created by ujjawal on 14/9/17.
 *
 */

public class WebView extends android.webkit.WebView {
    static final String BLANK = "about:blank";
    static final String FILE = "file:///";
    private static final String CACHE_PREFIX = "webarchive-";
    private static final String CACHE_EXTENSION = ".mht";

    private final HistoryWebViewClient mClient = new HistoryWebViewClient();


    public WebView(Context context) {
        this(context, null);
    }

    public WebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.setWebViewClient(mClient);
        init();
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        mClient.wrap(client);
    }

    @Override
    public void loadUrl(String url) {
        super.loadUrl(getCacheableUrl(url));
    }

    private void init() {
        enableCache();
        setLoadSettings();
    }

    private void enableCache() {
        getSettings().setAppCacheEnabled(true);
        getSettings().setAllowFileAccess(true);
        getSettings().setAppCachePath(getContext().getApplicationContext()
                .getCacheDir().getAbsolutePath());
        setCacheModeInternal();
    }

    private void setCacheModeInternal() {
        getSettings().setCacheMode(AppUtils.hasConnection(getContext()) ?
                WebSettings.LOAD_CACHE_ELSE_NETWORK : WebSettings.LOAD_CACHE_ONLY);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setLoadSettings() {
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setUseWideViewPort(true);
        getSettings().setJavaScriptEnabled(true);
    }

    private String getCacheableUrl(String url) {
        if (TextUtils.equals(url, BLANK) || TextUtils.equals(url, FILE)) {
            return url;
        }
        String cacheFileName = generateCacheFilename(url);
        setCacheModeInternal();
        if (getSettings().getCacheMode() != WebSettings.LOAD_CACHE_ONLY) {
            return url;
        }
        File cacheFile = new File(cacheFileName);
        return cacheFile.exists() ? Uri.fromFile(cacheFile).toString() : url;
    }

    private String generateCacheFilename(String url) {
        return getContext().getApplicationContext().getCacheDir().getAbsolutePath() +
                File.separator +
                CACHE_PREFIX +
                url.hashCode() +
                CACHE_EXTENSION;
    }



    static class HistoryWebViewClient extends WebViewClient {
        private WebViewClient mClient;

        @Override
        public void onPageStarted(android.webkit.WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            view.pageUp(true);
        }

        @Override
        public void onPageCommitVisible(android.webkit.WebView view, String url) {
            super.onPageCommitVisible(view, url);
            view.loadUrl("javascript:document.querySelector('header').setAttribute('style','display:none;');");
            view.loadUrl("javascript:document.querySelector('footer').setAttribute('style','display:none;');");
        }

        @Override
        public void onPageFinished(android.webkit.WebView view, String url) {
            super.onPageFinished(view, url);
            if (mClient != null) {
                mClient.onPageFinished(view, url);
            }
        }

        void wrap(WebViewClient client) {
            mClient = client;
        }
    }
}
