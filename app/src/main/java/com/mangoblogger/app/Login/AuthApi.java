package com.mangoblogger.app.Login;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by ujjawal on 2/11/17.
 */

public interface AuthApi {
 @GET("/ledzee1729/user/register/?username={username}&email={email}&nonce={nonce}&display_name={display_name}&notify=both&user_pass={password}")
    public void registerUser(@Path("username") String username,
                             @Path("email") String email,
                             @Path("nonce") String nonce,
                             @Path("display_name") String display_name,
                             @Path("password") String password,
                             Callback<String> callback);

 @GET("/ledzee1729/get_nonce/?controller=user&method={method}")
    public void getNonceId(@Path("method") String method,
                           Callback<String> callback);

 @GET("/ledzee1729/user/generate_auth_cookie/?username={username}&password={password}")
    public void loginUser(@Path("username") String username,
                          @Path("password") String password,
                          Callback<String> callback);
}
