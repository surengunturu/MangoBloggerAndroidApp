package com.mangoblogger.app.Login;

/**
 * Created by ujjawal on 3/11/17.
 *
 */

public class User {
    private String id;
    private String username;
    private String nicename;
    private String email;
    private String registered;
    private String displayname;
    private String description;

    public User(String id, String username, String nicename, String email,
                String registered, String displayname, String description) {
        this.id = id;
        this.username = username;
        this.nicename = nicename;
        this.email = email;
        this.registered = registered;
        this.displayname = displayname;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayname() {
        return displayname;
    }

    public String getId() {
        return id;
    }

    public String getNicename() {
        return nicename;
    }

    public String getRegistered() {
        return registered;
    }
}
