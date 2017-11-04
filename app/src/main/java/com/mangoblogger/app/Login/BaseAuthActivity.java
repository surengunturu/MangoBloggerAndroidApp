package com.mangoblogger.app.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.mangoblogger.app.MainActivity;
import com.mangoblogger.app.R;

import retrofit.RestAdapter;

/**
 * Created by ujjawal on 4/11/17.
 *
 */

public class BaseAuthActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;

    protected AuthApi initAdapter() {
        final RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://mangoblogger.com")
                .build();

        return adapter.create(AuthApi.class);
    }

    protected void startApp() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void showErrorDialog() {
        ErrorDialogFragment fragment = ErrorDialogFragment.newInstance(
                getString(R.string.title_error_no_network)
                , getString(R.string.message_error_no_network));

        fragment.show(getFragmentManager(), "FRAGMENT_ERROR");
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
