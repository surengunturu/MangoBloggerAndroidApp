package com.mangoblogger.app;



public class BlogModel {

    private String title = "Initial_Title";
    private String image = "Here I have image";
    private String description = "Initialize the description";

    public BlogModel() {}

    public BlogModel(String title, String description, String image) {
        this.title = title;
        this.image = image;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}
