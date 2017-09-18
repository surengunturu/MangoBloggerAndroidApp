package com.mangoblogger.app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
        view.findViewById(R.id.fab).setOnClickListener(this);
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
                try{
                    startBrowserIntent("https://www.facebook.com/mangoblogger/");
                }
                catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Please install a web browser or facebook app",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            case R.id.twitter:
                try{
                    startBrowserIntent("https://twitter.com/mangoblogger");
                }
                catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Please install a web browser or twitter app",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            case R.id.youtube:
                try{
                    startBrowserIntent("https://www.youtube.com/channel/UCCgxPOWNEqpcA60HnYg051w");
                }
                catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Please install a web browser or YouTube app",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            case R.id.linkedin:
                try{
                    startBrowserIntent("https://www.linkedin.com/company-beta/17919027");
                }
                catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Please install a web browser or Linkedin app",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            case R.id.insta:
                try{
                    startBrowserIntent("https://www.instagram.com/mangoblogger_analytics/");
                }
                catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Please install a web browser or Instagram app",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            case R.id.fab:
                startShareIntent();
                break;
        }
    }

    private void startShareIntent() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "https://play.google.com/store/apps/details?id=com.mangoblogger.app&utm_source=googleplaybadge&utm_campaign=website_homepage&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    private void startEmailIntent() {
        PackageManager pm = getActivity().getPackageManager();
        try {
            pm.getPackageInfo("com.google.android.gm", PackageManager.GET_META_DATA);
            Intent intent = new Intent(Intent.ACTION_SEND);
            getActivity().getIntent().setData(Uri.parse(("mailto: ")));
            String[] to = {"contact@mangoblogger.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, to);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Mail from Mangoblogger App");
            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Send Email"));
        }
        catch (PackageManager.NameNotFoundException e)
        {
            Toast.makeText(getActivity(), "Please install gmail app", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void startWhatsappIntent() {
        PackageManager pm = getActivity().getPackageManager();
        try{
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            String smsNumber = "3157518581";
            Uri uri = Uri.parse("smsto:" + smsNumber);
            Intent i = new Intent(Intent.ACTION_SENDTO, uri);
            i.putExtra("sms_body", "Hi");
            i.setPackage("com.whatsapp");
            startActivity(i);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            Toast.makeText(getActivity(), "Please install whatsapp app", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    private void startLocationIntent() {
        PackageManager pm = getActivity().getPackageManager();
        try {
            pm.getPackageInfo("com.google.android.gm", PackageManager.GET_META_DATA);
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 19.708079, 75.300217);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            getContext().startActivity(intent);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            Toast.makeText(getActivity(), "Please install Google map's app", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    private void startBrowserIntent(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(
                url));
        startActivity(browserIntent);
    }


}
