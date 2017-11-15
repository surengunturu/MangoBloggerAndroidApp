package com.mangobloggerandroid.app.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mangobloggerandroid.app.PreferenceUtil;
import com.mangobloggerandroid.app.R;
import com.mangobloggerandroid.app.activity.HomeActivity;
import com.mangobloggerandroid.app.util.AppUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SignupActivity extends BaseAuthActivity {

    private EditText inputUsername, inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private AuthApi mAuthApi;
    private String mNonceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        mAuthApi = initAdapter();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputUsername = (EditText) findViewById(R.id.username);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = inputUsername.getText().toString().trim();
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                /*if(mNonceId == null) {
                    getNonceId("user", "register");
                }*/

                if(TextUtils.isEmpty(username)) {
                    inputUsername.setError("Username is required");
                }

                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("Email Address is required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    inputPassword.setError("Password is required");
                    return;
                }

                if (password.length() < 6) {
                    inputPassword.setError("Password must be at least 6 characters long");
                    return;
                }
                registerUser(username, email, password);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void registerUser(final String username, final String email, final String password) {
        if(!AppUtils.isNetworkConnected(this)) {
            showErrorDialog();
        } else {
            showProgressDialog();
            mAuthApi.getNonceId("user", "register", new Callback<NonceIdResponse>() {
                @Override
                public void success(NonceIdResponse nonceIdResponse, Response response) {
                    Log.e("Success : NonceId", nonceIdResponse.getNonce());
                    if(nonceIdResponse.getStatus().equals("ok")) {
                        String nonceId = nonceIdResponse.getNonce();
                        signUp(nonceId, username, email, password);
                    } else {
                        hideProgressDialog();
                        showAuthError(R.id.error_text, "Sorry, couldn't able to SignIn. Please try again later!");
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("Failure : NonceId", error.getLocalizedMessage());
                    Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private void signUp(String nonceId, final String username, final String email,final String password) {
        mAuthApi.registerUser(username, email, nonceId, username, "both", password, new Callback<RegisterResponse>() {
            @Override
            public void success(RegisterResponse registerResponse, Response response) {
                if(registerResponse.getStatus().equals("ok")) {

                    User user = new User(registerResponse.getUser_id(), username, email, username,
                            true, registerResponse.getCookie());
                    PreferenceUtil.writeUserToPreferences(getApplicationContext(), user);
                    hideProgressDialog();
                    startApp();
                } else {
                    hideProgressDialog();
                    showAuthError(R.id.error_text, registerResponse.getError());
                }
            }
            @Override
            public void failure(RetrofitError error) {
                hideProgressDialog();
                Log.e("Failure : Register", error.getLocalizedMessage()+error.getUrl());
                showAuthError(R.id.error_text, "Unknown error occurred");
            }
        });
    }

    private void startApp() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}