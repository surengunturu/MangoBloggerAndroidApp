package com.mangoblogger.app.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mangoblogger.app.AppUtils;
import com.mangoblogger.app.PreferenceUtil;
import com.mangoblogger.app.R;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SignupActivity extends BaseAuthActivity {

    private EditText inputUsername, inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private ProgressDialog mProgressDialog;
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
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
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
                if(mNonceId == null) {
                    getNonceId("user", "registration");
                }

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
        getNonceId("user", "registration");
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private void getNonceId(String controller, String method) {
        if (!AppUtils.isNetworkConnected(this)) {
            showErrorDialog();
        } else {
            mAuthApi.getNonceId(controller, method, new Callback<NonceId>() {
                @Override
                public void success(NonceId nonceId, Response response) {
                    Log.e("Success : NonceId", nonceId.getNonce());
                    mNonceId = nonceId.getNonce();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("Failure : NonceId", error.getLocalizedMessage());
                    Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void registerUser(final String username, final String email, String password) {
        if(!AppUtils.isNetworkConnected(this)) {
            showErrorDialog();
        } else {
            mAuthApi.registerUser(username, email, mNonceId, username, "both", password, new Callback<RegisterResponse>() {
                @Override
                public void success(RegisterResponse registerResponse, Response response) {
                    if(registerResponse.getStatus().equals("ok")) {
                        User user = new User(registerResponse.getUser_id(), username, email, username,
                                true, registerResponse.getCookie());
                        PreferenceUtil.writeUserToPreferences(getApplicationContext(), user);
                        startApp();
                    } else {
                        showAuthError(R.id.error_text, registerResponse.getError());
                    }

                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }
}