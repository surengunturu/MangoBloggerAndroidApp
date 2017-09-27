package com.mangoblogger.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionFragment extends Fragment {

    private String title;
    private String description;
    private String image;

    ImageView imageView;
    TextView tilteView, descriptionView;

    public DescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            title = bundle.getString("TITLE");
            description = bundle.getString("DESCRIPTION");
            image = bundle.getString("IMAGE");
            tilteView = (TextView) view.findViewById(R.id.title);
            descriptionView = (TextView) view.findViewById(R.id.description);
            imageView = (ImageView) view.findViewById(R.id.image);
            tilteView.setText(title);
            descriptionView.setText(description);
            Glide.with(getActivity()).load(image).animate(R.anim.scale).into(imageView);
        }
        return view;
    }

}
