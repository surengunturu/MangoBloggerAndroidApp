package com.mangoblogger.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ms.square.android.expandabletextview.ExpandableTextView;


import java.util.List;


public class FirebaseDataAdapter extends RecyclerView.Adapter <FirebaseDataAdapter.ViewHolder> {

    private List<BlogModel> blogModels;
    private Context context;
    private LayoutInflater inflater;
    private final SparseBooleanArray mCollapsedStatus;



    protected FirebaseDataAdapter(List<BlogModel> blogModels, Context context) {
        this.blogModels = blogModels;
        inflater = LayoutInflater.from(context);
        this.context = context;
        mCollapsedStatus = new SparseBooleanArray();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_firebase_terms_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final BlogModel blogModel = blogModels.get(position);
        holder.title.setText(blogModel.getTitle());
        holder.description.setText(blogModel.getDescription(), mCollapsedStatus, position);
        if(!blogModel.getImage().equals("null")) {
             Glide.with(context).load(blogModel.getImage()).into(holder.firebase_image);
        }
    }

    @Override
    public int getItemCount() {
        return blogModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView firebase_image;
        ImageButton shareButton;
        ImageButton bookmarkButton;
        ImageButton likeButton;
        ExpandableTextView description;

        private ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (ExpandableTextView) itemView.findViewById(R.id.expand_text_view);
            firebase_image = (ImageView) itemView.findViewById(R.id.banner);
            likeButton = (ImageButton) itemView.findViewById(R.id.btnLike);
            bookmarkButton = (ImageButton) itemView.findViewById(R.id.btnBookmark);
            shareButton = (ImageButton) itemView.findViewById(R.id.btnShare);
            likeButton.setOnClickListener(this);
            bookmarkButton.setOnClickListener(this);
            shareButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
          if(title.getText() != null) {
              switch (view.getId()) {
                  case R.id.btnLike:
                      // implement like button on click
                      break;
                  case R.id.btnBookmark:
                      // implement bookmark button on click
                      break;
                  case R.id.btnShare:
                      String shareBody = title.getText() + "\n" + description.getText();
                      AppUtils.startShareIntent(context, shareBody);
              }
          }
        }
    }
}
