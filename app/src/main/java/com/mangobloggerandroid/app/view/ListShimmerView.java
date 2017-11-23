package com.mangobloggerandroid.app.view;

/**
 * Created by ujjawal on 23/11/17.
 */

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class ListShimmerView extends View implements ValueAnimator.AnimatorUpdateListener {
    private static final String TAG = ListShimmerView.class.getSimpleName();

    private static final int V_SPACING_DP = 16;
    private static final int H_SPACING_DP = 16;
    private static final int IMAGE_SIZE_DP = 40;
    private static final int LINE_HEIGHT_SP = 15;
    private static final int CORNER_RADIUS_DP = 2;
    private static final int ITEM_PATTERN_BG_COLOR = Color.WHITE;

    private static final int CENTER_ALPHA = 50;
    private static final int EDGE_ALPHA = 12;
    private static final int SHADER_COLOR_R = 170;
    private static final int SHADER_COLOR_G = 170;
    private static final int SHADER_COLOR_B = 170;
    private static final int CENTER_COLOR = Color.argb(CENTER_ALPHA, SHADER_COLOR_R, SHADER_COLOR_G, SHADER_COLOR_B);
    private static final int EDGE_COLOR = Color.argb(EDGE_ALPHA, SHADER_COLOR_R, SHADER_COLOR_G, SHADER_COLOR_B);

    private static final int ANIMATION_DURATION = 1500;

    private static final int LIST_ITEM_LINES = 3;

    private float vSpacing;
    private float hSpacing;
    private float lineHeight;
    private float imageSize;
    private float cornerRadius;

    private Bitmap listItemPattern;
    private Paint paint;
    private Paint shaderPaint;
    private int[] shaderColors;

    private ValueAnimator animator;

    public ListShimmerView(Context context) {
        super(context);
        init(context);
    }

    public ListShimmerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ListShimmerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        vSpacing = dpToPixels(metrics, V_SPACING_DP);
        hSpacing = dpToPixels(metrics, H_SPACING_DP);
        lineHeight = spToPixels(metrics, LINE_HEIGHT_SP);
        imageSize = dpToPixels(metrics, IMAGE_SIZE_DP);
        cornerRadius = dpToPixels(metrics, CORNER_RADIUS_DP);

        paint = new Paint();
        shaderPaint = new Paint();
        shaderPaint.setAntiAlias(true);
        shaderColors = new int[]{EDGE_COLOR, CENTER_COLOR, EDGE_COLOR};

        animator = ValueAnimator.ofFloat(-1f, 2f);
        animator.setDuration(ANIMATION_DURATION);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(this);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        if(isAttachedToWindow()) {
            float f = (float) valueAnimator.getAnimatedValue();
            updateShader(getWidth(), f);
            invalidate();
        } else {
            animator.cancel();
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        switch (visibility) {
            case VISIBLE:
                animator.start();
                break;
            case INVISIBLE:
            case GONE:
                animator.cancel();
                break;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateShader(w, -1f);
        if(h > 0 && w > 0) {
            preDrawItemPattern(w, h);
        } else {
            listItemPattern = null;
            animator.cancel();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(EDGE_COLOR);
        // draw gradient background
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), shaderPaint);
        if(listItemPattern != null) {
            // draw list item pattern
            canvas.drawBitmap(listItemPattern, 0, 0, paint);
        }
    }

    private void updateShader(float w, float f) {
        float left = w * f;
        LinearGradient shader = new LinearGradient(left, 0f, left + w, 0f,
                shaderColors, new float[]{0f, .5f, 1f}, Shader.TileMode.CLAMP);
        shaderPaint.setShader(shader);
    }

    private void preDrawItemPattern(int w, int h) {
        listItemPattern = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        // draw list items into the bitmap
        Canvas canvas = new Canvas(listItemPattern);
        Bitmap item = getItemBitmap(w);
        int top = 0;
        do {
            canvas.drawBitmap(item, 0, top, paint);
            top = top + item.getHeight();
        } while(top < canvas.getHeight());

        // only fill the rectangles with the background color
        canvas.drawColor(ITEM_PATTERN_BG_COLOR, PorterDuff.Mode.SRC_IN);
    }

    private Bitmap getItemBitmap(int w) {
        int h = calculatePatternHeight(LIST_ITEM_LINES);
        // we only need Alpha value in this bitmap
        Bitmap item = Bitmap.createBitmap(w, h, Bitmap.Config.ALPHA_8);

        Canvas canvas = new Canvas(item);
        canvas.drawColor(Color.argb(255, 0, 0, 0));

        Paint itemPaint = new Paint();
        itemPaint.setAntiAlias(true);
        itemPaint.setColor(Color.argb(0, 0, 0, 0));
        itemPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        // avatar
        RectF rectF = new RectF(vSpacing, hSpacing, vSpacing + imageSize, hSpacing + imageSize);
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, itemPaint);

        float textLeft = rectF.right + hSpacing;
        float textRight = canvas.getWidth() - vSpacing;

        // title line
        float titleWidth = (float) ((textRight - textLeft) * 0.5);
        rectF.set(textLeft, hSpacing, textLeft + titleWidth, hSpacing + lineHeight);
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, itemPaint);

        // timestamp
        float timeWidth = (float) ((textRight - textLeft) * 0.2);
        rectF.set(textRight - timeWidth, hSpacing, textRight, hSpacing + lineHeight);
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, itemPaint);

        // text lines
        int line = LIST_ITEM_LINES - 1;
        while(line > 0) {
            float lineTop = rectF.bottom + hSpacing;
            rectF.set(textLeft, lineTop, textRight, lineTop + lineHeight);
            canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, itemPaint);
            line--;
        }
        return item;
    }

    private int calculatePatternHeight(int lines) {
        return (int) ((lines * lineHeight) + (hSpacing * (lines + 1)));
    }

    private float dpToPixels(DisplayMetrics metrics, int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    private float spToPixels(DisplayMetrics metrics, int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics);
    }
}
