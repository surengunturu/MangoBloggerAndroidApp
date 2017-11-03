package com.mangoblogger.app.Login;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by ujjawal on 2/11/17.
 *
 */

public interface AuthApi {
 @GET("/ledzee1729/user/register/")
    public void registerUser(@Query("username") String username,
                             @Query("email") String email,
                             @Query("nonce") String nonce,
                             @Query("display_name") String display_name,
                             @Query("notify") String notify,
                             @Query("password") String password,
                             Callback<String> callback);

 @GET("/ledzee1729/get_nonce/")
    public void getNonceId(@Query("controller") String controller,
                           @Query("method") String method,
                           Callback<NonceId> callback);

 @GET("/ledzee1729/user/generate_auth_cookie/?username={username}&password={password}")
    public void loginUser(@Query("username") String username,
                          @Query("password") String password,
                          Callback<String> callback);
}
