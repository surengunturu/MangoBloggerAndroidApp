package com.mangobloggerandroid.app.Login;

/**
 * Created by ujjawal on 3/11/17.
 *
 */

public class LoginResponse {
    private String status;
    private String error;
    private String cookie;
    private User user;

    public LoginResponse(String status, String cookie, User user) {
        this.status = status;
        this.cookie = cookie;
        this.user = user;
        user.setCookie(cookie);
    }

    public LoginResponse(String status, String error) {
        this.status = status;
        this.error = error;
    }

    public String getCookie() {
        return cookie;
    }

    public String getError() {
        return error;
    }

    public User getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

    public String getUsername() {
        return user.getUsername();
    }

    public String getDescription() {
        return user.getDescription();
    }
}
