package com.mangoblogger.app.Login;

/**
 * Created by ujjawal on 4/11/17.
 *
 */

public class RegisterResponse {
    private String status;
    private String error;
    private String cookie;
    private String user_id;

    public RegisterResponse(String status, String cookie, String user_id) {
        this.status = status;
        this.cookie = cookie;
        this.user_id = user_id;
    }

    public RegisterResponse(String status, String error) {
        this.status = status;
        this.error = error;
    }

    public String getCookie() {
        return cookie;
    }

    public String getError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    public String getUser_id() {
        return user_id;
    }
}
