package com.mangoblogger.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.Locale;



public class AboutFragment extends Fragment{

    View view;
    LinearLayout email, find_location, send_msg;
    ImageView facebook, twitter, youtube, linkedin, insta;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about, container, false);

        email = (LinearLayout) view.findViewById(R.id.send_email);
        send_msg = (LinearLayout) view.findViewById(R.id.send_whatsapp_msg);
        find_location = (LinearLayout) view.findViewById(R.id.find_location);
        facebook = (ImageView) view.findViewById(R.id.facebook);
        twitter = (ImageView) view.findViewById(R.id.twitter);
        youtube = (ImageView) view.findViewById(R.id.youtube);
        linkedin = (ImageView) view.findViewById(R.id.linkedin);
        insta = (ImageView) view.findViewById(R.id.insta);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                getActivity().getIntent().setData(Uri.parse(("mailto: ")));
                String[] to = {"contact@mangoblogger.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, to);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Mail from Mangoblogger App");
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        find_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", 19.708079, 75.300217);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                getContext().startActivity(intent);
            }
        });

        send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String smsNumber = "3157518581";
                Uri uri = Uri.parse("smsto:" + smsNumber);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.putExtra("sms_body", "Hi");
                i.setPackage("com.whatsapp");
                startActivity(i);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(
                        "https://www.facebook.com/mangoblogger/"));
                startActivity(browserIntent);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(
                        "https://twitter.com/mangoblogger"));
                startActivity(browserIntent);
            }
        });

        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(
                        "https://www.linkedin.com/company-beta/17919027"));
                startActivity(browserIntent);
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(
                        "https://www.youtube.com/channel/UCCgxPOWNEqpcA60HnYg051w"));
                startActivity(browserIntent);
            }
        });

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(
                        "https://www.instagram.com/mangoblogger_analytics/"));
                startActivity(browserIntent);
            }
        });
        return view;
    }
}
