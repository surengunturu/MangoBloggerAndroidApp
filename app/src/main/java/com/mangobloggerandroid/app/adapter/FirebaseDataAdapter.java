package com.mangobloggerandroid.app.adapter;

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
import com.mangobloggerandroid.app.util.AppUtils;
import com.mangobloggerandroid.app.Database.DatabaseHelper;
import com.mangobloggerandroid.app.R;
import com.mangobloggerandroid.app.model.BlogModel;
import com.ms.square.android.expandabletextview.ExpandableTextView;


import java.util.List;


public class FirebaseDataAdapter extends RecyclerView.Adapter <FirebaseDataAdapter.ViewHolder> {

    private List<BlogModel> blogModelList;
    private Context context;
    private LayoutInflater inflater;
    private final SparseBooleanArray mCollapsedStatus;
    private int mCurrentPosition;
    private BlogModel mCurrentBlogModel;
    private int[] mPositionList;



    public FirebaseDataAdapter(List<BlogModel> blogModelList, Context context) {
        this.blogModelList = blogModelList;
//        inflater = LayoutInflater.from(context);
        this.context = context;
//        mPositionList = DatabaseHelper.getBookmarkPositionList(context, true);
        mCollapsedStatus = new SparseBooleanArray();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_firebase_terms_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
       /* if(mPositionList != null) {
            for(int i:mPositionList) {
                if(i==position) {
                    holder.bookmarkButton.setImageResource(R.mipmap.ic_bookmark_on);
                }
            }
        }*/
        mCurrentBlogModel = blogModelList.get(position);
        mCurrentPosition = holder.getAdapterPosition();
        holder.title.setText(mCurrentBlogModel.getTitle());
        holder.description.setText(android.text.Html.fromHtml(mCurrentBlogModel.getDescription()).toString(), mCollapsedStatus, position);
        if(!mCurrentBlogModel.getImage().equals("null") && context != null) {
             Glide.with(context).load(mCurrentBlogModel.getImage()).into(holder.firebase_image);
        }
    }

    @Override
    public int getItemCount() {
        return blogModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView firebase_image;
        ImageButton shareButton;
        ImageButton bookmarkButton;
//        ButtonAnimationView likeButton;
        ExpandableTextView description;

        private ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (ExpandableTextView) itemView.findViewById(R.id.expand_text_view);
            firebase_image = (ImageView) itemView.findViewById(R.id.banner);
//            likeButton = (ButtonAnimationView) itemView.findViewById(R.id.btnLike);
//            bookmarkButton = (ImageButton) itemView.findViewById(R.id.btnBookmark);
            shareButton = (ImageButton) itemView.findViewById(R.id.btnShare);
//            likeButton.setOnClickListener(this);
//            bookmarkButton.setOnClickListener(this);
            shareButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
          if(mCurrentBlogModel != null) {
              switch (view.getId()) {
                /*  case R.id.btnLike:
                      // implement like button on click
                      break;*/
                  /*case R.id.btnBookmark:
                      DatabaseHelper.addBookmark(context, mCurrentBlogModel,
                              mCurrentPosition, true);
                      break;*/
                  case R.id.btnShare:
                      String shareBody = title.getText() + "\n\n" + description.getText()+ context.getString(R.string.card_share_body_extra);
                      AppUtils.startShareIntent(context, shareBody);
              }
          }
        }
    }
}
