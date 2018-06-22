package com.mangobloggerandroid.app.Login;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.mangobloggerandroid.app.util.PreferenceUtil;
import com.mangobloggerandroid.app.R;
import com.mangobloggerandroid.app.util.AppUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends BaseAuthActivity {

    private static final String TAG = "Main_Activity";

    private SignInButton mGoogleBtn;



    private FirebaseAuth.AuthStateListener mAuthListner;



    private EditText inputEmail, inputPassword;
    private AuthApi mAuthApi;
    private Button btnLogin, btnReset; //btnSignup,
    private FirebaseAnalytics mFirebaseAnalytics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the view now
        setContentView(R.layout.activity_login);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

//        mAuth = FirebaseAuth.getInstance();

        mAuthApi = initAdapter();

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        //btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        mGoogleBtn = (SignInButton) findViewById(R.id.googleBtn);


        configGoogleSignIn();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                if(!validateForm(inputEmail, inputPassword)) {
                    return;
                }
                signIn(email, password);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
            }
        });

//        btnSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
//                finish();
//            }
//        });

        mGoogleBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view) {

                signInWithGoogle();
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(true) {
            startApp();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAnalytics.setCurrentScreen(this, getClass().getSimpleName(), "Log-In");
    }


    private void signIn(String emailId, String password) {
        if (!AppUtils.isNetworkConnected(this)) {
            showErrorDialog();
        } else {
            showProgressDialog();
            mAuthApi.loginUser(emailId, password, new Callback<LoginResponse>() {
                @Override
                public void success(LoginResponse loginResponse, Response response) {
                    if(loginResponse.getStatus().equals("ok")) {
                        loginResponse.getUser().setLoggedIn(true);
                        PreferenceUtil.writeUserToPreferences(getApplicationContext(), loginResponse.getUser());
                        startApp();
                    } else {
                        showAuthError(R.id.error_text, loginResponse.getError());
                    }
                    hideProgressDialog();
                }

                @Override
                public void failure(RetrofitError error) {
                    hideProgressDialog();
                    Log.e("Failure : Login", error.getLocalizedMessage());
                    Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }




    private boolean validateForm(EditText emailField, EditText passwordField) {
        boolean result = true;
        if (TextUtils.isEmpty(emailField.getText().toString())) {
            emailField.setError("Required");
            result = false;
        } else {
            emailField.setError(null);
        }

        if (TextUtils.isEmpty(passwordField.getText().toString())) {
            passwordField.setError("Required");
            result = false;
        } else {
            passwordField.setError(null);
        }

        return result;
    }






    /*private void signIn() {
        showProgressDialog();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
                Toast.makeText(LoginActivity.this, "Authentication failed.Please try again after some times",
                        Toast.LENGTH_SHORT).show();
                Log.e("Login : failed", result.getStatus().getStatusMessage());
                hideProgressDialog();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        hideProgressDialog();
                        startApp();
                        Log.d(TAG, "SignInWith : onComplete:" + task.isSuccessful());
                    }
                });
    }*/
}

