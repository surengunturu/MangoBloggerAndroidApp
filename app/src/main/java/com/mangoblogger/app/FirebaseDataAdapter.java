package com.mangoblogger.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mangoblogger.app.widget.ExpandableTextView;

import java.util.List;


public class FirebaseDataAdapter extends RecyclerView.Adapter <FirebaseDataAdapter.ViewHolder> {

    List<BlogModel> blogModels;
    Context context;
    LayoutInflater inflater;
    int prevPosition=0;
    int count;
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
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final BlogModel blogModel = blogModels.get(position);
        holder.title.setText(blogModel.getTitle());
        holder.description.setText(blogModel.getDescription());
        int count = blogModel.getDescription().split("[!?.:]+").length;


        if(count < 3) {
            holder.buttonToggle.setVisibility(View.GONE);
        } else {
            setExpandableTextView(holder);
        }
//        setExpandableTextView(holder);
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
        ExpandableTextView description;
        ImageView firebase_image;
        Button buttonToggle;


        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (ExpandableTextView) itemView.findViewById(R.id.description);
            firebase_image = (ImageView) itemView.findViewById(R.id.banner);
            buttonToggle =  (Button) itemView.findViewById(R.id.button_toggle);

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
    
    private void setExpandableTextView(final ViewHolder holder) {
        holder.description.setAnimationDuration(750L);

        // set interpolators for both expanding and collapsing animations
        holder.description.setInterpolator(new OvershootInterpolator());


        // or set them separately
        holder.description.setExpandInterpolator(new OvershootInterpolator());
        holder.description.setCollapseInterpolator(new OvershootInterpolator());

        // toggle the ExpandableTextView
        holder.buttonToggle.setOnClickListener(new View.OnClickListener()
        {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(final View v)
            {
                holder.description.toggle();
                holder.buttonToggle.setText(holder.description.isExpanded() ? R.string.collapse : R.string.expand);
            }
        });

        // but, you can also do the checks yourself
        holder.buttonToggle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                if (holder.description.isExpanded())
                {
                    holder.description.collapse();
                    holder.buttonToggle.setText(R.string.expand);
                }
                else
                {
                    holder.description.expand();
                    holder.buttonToggle.setText(R.string.collapse);
                }
            }
        });

        // listen for expand / collapse events
        holder.description.setOnExpandListener(new ExpandableTextView.OnExpandListener()
        {
            @Override
            public void onExpand(final ExpandableTextView view)
            {
//                Log.d(TAG, "ExpandableTextView expanded");
            }

            @Override
            public void onCollapse(final ExpandableTextView view)
            {
//                Log.d(TAG, "ExpandableTextView collapsed");
            }
        });
    }
}
