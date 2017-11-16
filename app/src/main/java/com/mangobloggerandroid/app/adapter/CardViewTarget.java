package com.mangobloggerandroid.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.CardView;
import android.widget.LinearLayout;

/**
 * Created by ujjawal on 16/11/17.
 */

public class CardViewTarget extends ViewGroupTarget<Bitmap> {

    private Context context;

    public CardViewTarget(Context context, CardView cardView) {

        super(cardView);

        this.context = context;
    }

    /**
     * Sets the {@link android.graphics.Bitmap} on the view using
     * {@link android.widget.ImageView#setImageBitmap(android.graphics.Bitmap)}.
     *
     * @param resource The bitmap to display.
     */
    @Override
    protected void setResource(Bitmap resource) {

        view.setBackground(new BitmapDrawable(context.getResources(), resource));
    }

}
