package com.mangobloggerandroid.app.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mangobloggerandroid.app.R;
import com.mangobloggerandroid.app.activity.HomeActivity;

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



    public void showAuthError(int resId, String errorText) {
        TextView errorTextView = (TextView) findViewById(resId);
        errorTextView.setText(errorText);
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
            mProgressDialog.setMessage("Working...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
