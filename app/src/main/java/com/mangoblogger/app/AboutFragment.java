package com.mangoblogger.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

/**
 * Created by karthikprasad on 7/29/17.
 *  Fragment to show About view
 */

public class AboutFragment extends Fragment implements View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        view.findViewById(R.id.send_email).setOnClickListener(this);
        view.findViewById(R.id.send_whatsapp_msg).setOnClickListener(this);
        view.findViewById(R.id.find_location).setOnClickListener(this);
        view.findViewById(R.id.facebook).setOnClickListener(this);
        view.findViewById(R.id.twitter).setOnClickListener(this);
        view.findViewById(R.id.youtube).setOnClickListener(this);
        view.findViewById(R.id.linkedin).setOnClickListener(this);
        view.findViewById(R.id.insta).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_email:
                startEmailIntent();
                break;
            case R.id.send_whatsapp_msg:
                startWhatsappIntent();
                break;
            case R.id.find_location:
                startLocationIntent();
                break;
            case R.id.facebook:
                startBrowserIntent("https://www.facebook.com/mangoblogger/");
                break;
            case R.id.twitter:
                startBrowserIntent("https://twitter.com/mangoblogger");
                break;
            case R.id.youtube:
                startBrowserIntent("");
                break;
            case R.id.linkedin:
                startBrowserIntent("https://www.linkedin.com/company-beta/17919027");
                break;
            case R.id.insta:
                startBrowserIntent("https://www.youtube.com/channel/UCCgxPOWNEqpcA60HnYg051w");
        }
    }


    private void startEmailIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        getActivity().getIntent().setData(Uri.parse(("mailto: ")));
        String[] to = {"contact@mangoblogger.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Mail from Mangoblogger App");
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    private void startWhatsappIntent() {
        String smsNumber = "3157518581";
        Uri uri = Uri.parse("smsto:" + smsNumber);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.putExtra("sms_body", "Hi");
        i.setPackage("com.whatsapp");
        startActivity(i);
    }

    private void startLocationIntent() {
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 19.708079, 75.300217);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        getContext().startActivity(intent);
    }

    private void startBrowserIntent(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(
                url));
        startActivity(browserIntent);
    }


}
