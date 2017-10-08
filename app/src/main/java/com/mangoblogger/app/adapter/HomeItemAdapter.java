package com.mangoblogger.app.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mangoblogger.app.R;
import com.mangoblogger.app.model.HomeItem;

import java.util.List;

/**
 * Created by ujjawal on 8/10/17.
 *
 */

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.ViewHolder> {

    private List<HomeItem> mHomeItems;
    private int mCardSize;
    OnItemClickListener onItemClickListener;


    public HomeItemAdapter(int cardSize, List<HomeItem> homeItems) {
        mHomeItems = homeItems;
        mCardSize = cardSize;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mCardSize == HomeBaseAdapter.CARD_SIZE_SMALL) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_card_small, parent, false));
        } else if(mCardSize == HomeBaseAdapter.CARD_SIZE_MEDIUM){
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_card_medium, parent, false));
        } else if(mCardSize == HomeBaseAdapter.CARD_SIZE_PAGER) {
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_card_pager, parent, false));
        } else {
            return null; // not a good practice
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeItem item = mHomeItems.get(position);

//        holder.imageView.setImageResource(item.getDrawable());
        holder.parentCardView.setBackgroundResource(item.getDrawable());
        holder.titleText.setText(item.getName());
        if(mCardSize == HomeBaseAdapter.CARD_SIZE_MEDIUM
                || mCardSize == HomeBaseAdapter.CARD_SIZE_PAGER) {
            holder.titleText.setText(item.getName());
            holder.subtitleText.setText(item.getExtra());
        }
//        holder.ratingTextView.setText(String.valueOf(item.getRating()));

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mHomeItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        CardView parentCardView;
        TextView titleText;
        TextView subtitleText;
//        public TextView ratingTextView;

        private ViewHolder(View itemView) {
            super(itemView);
//            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            parentCardView = (CardView) itemView.findViewById(R.id.parent_card);
            titleText = (TextView) itemView.findViewById(R.id.title);
            subtitleText = (TextView) itemView.findViewById(R.id.sub_title);
            parentCardView.setOnClickListener(this);
//            ratingTextView = (TextView) itemView.findViewById(R.id.ratingTextView);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                HomeItem item = mHomeItems.get(getAdapterPosition());
                onItemClickListener.itemClick(item, getAdapterPosition());
            }

        }

    }

    public interface OnItemClickListener {
        void itemClick(HomeItem item, int Position);
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        this.onItemClickListener = (OnItemClickListener) onItemClickListener;
    }

}


