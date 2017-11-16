package com.mangobloggerandroid.app.model;

/**
 * Created by ujjawal on 16/11/17.
 */

public class Author {
    private String name;
    private String description;

    public Author(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
