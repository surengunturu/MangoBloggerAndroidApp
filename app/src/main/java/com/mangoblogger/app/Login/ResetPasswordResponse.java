package com.mangoblogger.app.Login;

/**
 * Created by ujjawal on 5/11/17.
 */

public class ResetPasswordResponse {
    String status;
    String error;
    String msg;

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        if(status.equals("ok")) {
            return msg;
        } else if(status.equals("error")) {
            return error;
        }
        return "Some unknown shit happened!";
    }
}
