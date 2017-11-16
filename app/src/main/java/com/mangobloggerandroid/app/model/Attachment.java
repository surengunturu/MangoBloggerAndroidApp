package com.mangobloggerandroid.app.model;

/**
 * Created by ujjawal on 16/11/17.
 */

public class Attachment {
    private String url;
    private String title;

    public Attachment(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }
}
