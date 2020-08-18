package com.hewking.develop.util;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import androidx.appcompat.widget.Toolbar;

import com.hewking.develop.R;

/**
 * @author: jianhao
 * @create: 2020/8/18
 * @description:
 */
public class TransparentToolbarManager {

    private Toolbar mToolbar;
    private ColorDrawable colorDrawable;
    public static final int MAX_ALPHA = 255, MIN_ALPHA = 0;

    public TransparentToolbarManager(Toolbar mToolbar) {
        this.mToolbar = mToolbar;
        this.colorDrawable = new ColorDrawable(mToolbar.getContext().getResources().getColor(R.color.white));
    }

    public TransparentToolbarManager(Toolbar mToolbar, ColorDrawable colorDrawable) {
        this.mToolbar = mToolbar;
        this.colorDrawable = colorDrawable;
    }

    //Fading toolbar
    public void manageFadingToolbar(int scrollDistance) {
        if (mToolbar != null && colorDrawable != null) {
            //FadeinAndOut according to the horizontal scrollValue
            if (scrollDistance <= MAX_ALPHA && scrollDistance >= MIN_ALPHA) {
                setToolbarAlpha(scrollDistance);
            } else if (scrollDistance > MAX_ALPHA) {
                setToolbarAlpha(MAX_ALPHA);
            }
        }
    }


    public void setToolbarAlpha(int i) {
        colorDrawable.setAlpha(i);
        if (isSupport(16)) {
            mToolbar.setBackground(colorDrawable);
        } else {
            mToolbar.setBackgroundDrawable(colorDrawable);
        }
    }

    public static boolean isSupport(int apiLevel) {
        return Build.VERSION.SDK_INT >= apiLevel;
    }

}
