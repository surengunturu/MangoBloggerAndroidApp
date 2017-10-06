package com.mangoblogger.app.AnimatedButton;

import android.support.annotation.DrawableRes;

/**
 * Created by Miroslaw Stanek on 21.12.2015.
 * Modified by Joel Dean
 * copied by andy1729 from https://github.com/jd-alexander/LikeButton
 * We must give credit to author
 */
public class Icon {
    private int onIconResourceId;
    private int offIconResourceId;
    private IconType iconType;

    public Icon(@DrawableRes int onIconResourceId,@DrawableRes int offIconResourceId, IconType iconType) {
        this.onIconResourceId = onIconResourceId;
        this.offIconResourceId = offIconResourceId;
        this.iconType = iconType;
    }

    public int getOffIconResourceId() {
        return offIconResourceId;
    }

    public void setOffIconResourceId(@DrawableRes int offIconResourceId) {
        this.offIconResourceId = offIconResourceId;
    }

    public int getOnIconResourceId() {
        return onIconResourceId;
    }

    public void setOnIconResourceId(@DrawableRes int onIconResourceId) {
        this.onIconResourceId = onIconResourceId;
    }

    public IconType getIconType() {
        return iconType;
    }

    public void setIconType(IconType iconType) {
        this.iconType = iconType;
    }
}
