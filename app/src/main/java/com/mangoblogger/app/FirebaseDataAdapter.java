package com.mangoblogger.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class FirebaseDataAdapter extends RecyclerView.Adapter <FirebaseDataAdapter.ViewHolder> {

    List<BlogModel> blogModels;
    Context context;
    LayoutInflater inflater;
    int prevPosition=0;
  OnItemClickListener onItemClickListener;


    public FirebaseDataAdapter(List<BlogModel> blogModels, Context context) {
        this.blogModels = blogModels;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_firebase_terms_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FirebaseDataAdapter.ViewHolder holder, int position) {

        BlogModel blogModel = blogModels.get(position);
        holder.title.setText(blogModel.getTitle());
//        holder.description.setText(blogModel.getDescription());
//        Glide.with(context).load(blogModel.getImage()).into(holder.firebase_image);

    }

    @Override
    public int getItemCount() {
        return blogModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
//        TextView description;
        ImageView firebase_image;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
//            description = (TextView) itemView.findViewById(R.id.description);
            firebase_image = (ImageView) itemView.findViewById(R.id.banner);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            if (onItemClickListener != null) {
                onItemClickListener.itemClick(blogModels.get(getAdapterPosition()).getTitle(),
                        blogModels.get(getAdapterPosition()).getDescription(), blogModels.get(getAdapterPosition()).getImage(), getAdapterPosition());
            }

        }
    }


    public interface OnItemClickListener {
        void itemClick(String title, String description, String image, int Position);
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = (OnItemClickListener) onItemClickListener;
    }
}
