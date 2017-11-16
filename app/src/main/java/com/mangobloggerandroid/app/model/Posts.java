package com.mangobloggerandroid.app.model;

/**
 * Created by ujjawal on 16/11/17.
 */

public class Posts {
    private String url;
    private String title_plain;
    private Author author;
    private Attachment attachment;

    public Posts(String url, String title_plain, Author author, Attachment attachment) {
        this.url = url;
        this.title_plain = title_plain;
        this.author = author;
        this.attachment = attachment;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle_plain() {
        return title_plain;
    }

    public Author getAuthor() {
        return author;
    }

    public Attachment getAttachment() {
        return attachment;
    }
}
