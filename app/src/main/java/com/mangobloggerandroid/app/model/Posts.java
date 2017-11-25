package com.mangobloggerandroid.app.model;

import java.util.List;

/**
 * Created by ujjawal on 16/11/17.
 */

public class Posts {
    private String url;
    private String title_plain;
    private Author author;
    private String content;
    private List<Attachment> attachments;

    public Posts(String url,String content, String title_plain, Author author, List<Attachment> attachments) {
        this.url = url;
        this.title_plain = title_plain;
        this.author = author;
        this.content = content;
        this.attachments = attachments;
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
        if(attachments.size() > 0) {
            return attachments.get(0);
        }
        return null;
    }

    public String getContent() {
        return content;
    }
}
