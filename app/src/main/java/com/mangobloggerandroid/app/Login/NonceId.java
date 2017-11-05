package com.mangobloggerandroid.app.Login;

/**
 * Created by ujjawal on 3/11/17.
 */

public class NonceId {
    private String status;
    private String controller;
    private String method;
    private String nonce;

    public NonceId(String status, String controller, String method, String nonce) {
        this.status = status;
        this.controller = controller;
        this.method = method;
        this.nonce = nonce;
    }

    public String getNonce() {
        return nonce;
    }
}
