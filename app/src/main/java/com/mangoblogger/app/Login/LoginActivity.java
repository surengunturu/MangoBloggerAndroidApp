package com.mangoblogger.app.Login;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.mangoblogger.app.AppUtils;
import com.mangoblogger.app.R;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Main_Activity";

    private EditText inputEmail, inputPassword;
    private AuthApi mAuthApi;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set the view now
        setContentView(R.layout.activity_login);

        initAdapter();

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                getNonceId("user", "register");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initAdapter() {
        final RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://mangoblogger.com")
                .build();

        mAuthApi = adapter.create(AuthApi.class);
    }

    private void getNonceId(String controller, String method) {
        if (!AppUtils.isNetworkConnected(this)) {
            ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                    getString(R.string.title_error_no_network)
                    , getString(R.string.message_error_no_network));

            fragment.show(getFragmentManager(), "FRAGMENT_ERROR");
        } else {
            mAuthApi.getNonceId(controller, method, new Callback<NonceId>() {
                @Override
                public void success(NonceId nonceId, Response response) {
                    Log.e("Success : NonceId", nonceId.getNonce());
                    Toast.makeText(getApplicationContext(), nonceId.getNonce(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("Failure : NonceId", error.getLocalizedMessage());
                    Toast.makeText(getApplicationContext(), error.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                }
            });



        }

    }

    private void signIn() {

    }
}

