package com.mangoblogger.app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by karthikprasad on 7/29/17.
 *  Fragment to show About view
 */

public class AboutFragment extends Fragment implements View.OnClickListener {

    private String mAbout;
    private String mContactNumber;
    private String mCountryCode;
    private String mAddress;
    private String mGeoLatitude;
    private String mGeoLongitude;

    public static AboutFragment newInstance(String about, String countryCode, String contactNumber,
                                            String address, String geoLatitude, String geoLongitude) {
        AboutFragment fragment = new AboutFragment();
        Bundle args = new Bundle();
        args.putString(MainActivity.ABOUT_KEY, about);
        args.putString(MainActivity.COUNTRY_CODE_KEY, countryCode);
        args.putString(MainActivity.CONTACT_NUMBER_KEY, contactNumber);
        args.putString(MainActivity.ADDRESS_KEY, address);
        args.putString(MainActivity.GEO_LATITUDE_KEY, geoLatitude);
        args.putString(MainActivity.GEO_LONGITUDE_KEY, geoLongitude);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAbout = getArguments().getString(MainActivity.ABOUT_KEY);
            mCountryCode = getArguments().getString(MainActivity.COUNTRY_CODE_KEY);
            mContactNumber = getArguments().getString(MainActivity.CONTACT_NUMBER_KEY);
            mAddress = getArguments().getString(MainActivity.ADDRESS_KEY);
            mGeoLatitude = getArguments().getString(MainActivity.GEO_LATITUDE_KEY);
            mGeoLongitude = getArguments().getString(MainActivity.GEO_LONGITUDE_KEY);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String contact = mCountryCode + " " + mContactNumber;

        TextView aboutView = (TextView) view.findViewById(R.id.about);
        aboutView.setText(mAbout);
        TextView contactView = (TextView) view.findViewById(R.id.contact_number);
        contactView.setText(contact);
        TextView addressView = (TextView) view.findViewById(R.id.address);
        addressView.setText(mAddress);
        view.findViewById(R.id.send_email_mangoblogger).setOnClickListener(this);
        view.findViewById(R.id.send_whatsapp_msg).setOnClickListener(this);
        view.findViewById(R.id.find_location).setOnClickListener(this);
        view.findViewById(R.id.facebook).setOnClickListener(this);
        view.findViewById(R.id.twitter).setOnClickListener(this);
        view.findViewById(R.id.youtube).setOnClickListener(this);
        view.findViewById(R.id.linkedin).setOnClickListener(this);
        view.findViewById(R.id.insta).setOnClickListener(this);
        view.findViewById(R.id.share).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_email_mangoblogger:
                startEmailIntent();
                break;
            case R.id.send_whatsapp_msg:
                startWhatsappIntent();
                break;
            case R.id.find_location:
                startLocationIntent();
                break;
            case R.id.facebook:
                try {
                    startBrowserIntent("https://www.facebook.com/mangoblogger/");
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Please install a web browser or Facebook app",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            case R.id.twitter:
                try {
                    startBrowserIntent("https://twitter.com/mangoblogger");
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Please install a web browser or Twitter app",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            case R.id.youtube:
                try {
                    startBrowserIntent("https://www.youtube.com/channel/UCCgxPOWNEqpcA60HnYg051w");
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Please install a web browser or Youtube app",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            case R.id.linkedin:
                try {
                    startBrowserIntent("https://www.linkedin.com/company-beta/17919027");
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Please install a web browser or Linkedin app",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            case R.id.insta:
                try {
                    startBrowserIntent("https://www.instagram.com/mangoblogger_analytics/");
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "Please install a web browser or instagram app",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            case R.id.share:
                startShareIntent();
        }
    }

    private void startShareIntent() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "https://i2.wp.com/play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png?fit=980%2C50&ssl=1";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void startEmailIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if(intent.resolveActivity(getContext().getPackageManager()) != null) {
            getActivity().getIntent().setData(Uri.parse(("mailto: ")));
            String[] to = {"contact@mangoblogger.com"};
            intent.putExtra(Intent.EXTRA_EMAIL, to);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Mail from Mangoblogger App");
            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Send Email"));
        }
    }

    private void startWhatsappIntent() {
        String smsNumber = mContactNumber;
        Uri uri = Uri.parse("smsto:" + smsNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        if(intent.resolveActivity(getContext().getPackageManager()) != null) {
            intent.putExtra("sms_body", "Hi");
            intent.setPackage("com.whatsapp");
            startActivity(intent);
        }
    }

    private void startLocationIntent() {
        Uri gmmIntentUri = Uri.parse("geo:"+mGeoLatitude+","+mGeoLongitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private void startBrowserIntent(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(
                url));
        if(browserIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(browserIntent);
        }
    }


}
