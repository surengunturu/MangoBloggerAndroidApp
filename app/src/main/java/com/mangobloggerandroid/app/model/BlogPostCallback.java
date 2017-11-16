package com.mangobloggerandroid.app.model;

import java.util.List;

/**
 * Created by ujjawal on 16/11/17.
 */

public class BlogPostCallback {
    private String status;
    private String count;
    private List<Posts> posts;

    public BlogPostCallback(String status, String count, List<Posts> posts) {
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

    public List<Posts> getPosts() {
        return posts;
    }
}
