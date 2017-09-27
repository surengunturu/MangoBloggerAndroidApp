package com.mangoblogger.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DescriptionActivity extends Activity {

    private String title;
    private String description;
    private String image;

   private ImageView imageView;
   private TextView tilteView, descriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        // Just passing data from FirebaseList activity to this activity
        Intent intent= getIntent();
        title = intent.getStringExtra("TITLE");
        description = intent.getStringExtra("DESCRIPTION");
        image = intent.getStringExtra("IMAGE");

        tilteView = (TextView) findViewById(R.id.activity_title);
        descriptionView = (TextView) findViewById(R.id.activity_des);
        imageView = (ImageView) findViewById(R.id.activity_image);
        tilteView.setText(title);
        descriptionView.setText(description);
        Glide.with(getApplicationContext()).load(image).animate(R.anim.scale).into(imageView);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.enter_anim1,R.anim.exit_anim1);


    }
}
