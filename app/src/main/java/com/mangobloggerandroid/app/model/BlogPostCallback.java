package com.mangobloggerandroid.app.model;

/**
 * Created by ujjawal on 16/11/17.
 */

public class BlogPostCallback {
    private String status;
    private String count;
    private Posts posts;

    public BlogPostCallback(String status, String count, Posts posts) {
        this.status = status;
        this.count = count;
        this.posts = posts;
    }

    public String getStatus() {
        return status;
    }

    public String getCount() {
        return count;
    }

    public Posts getPosts() {
        return posts;
    }
}
