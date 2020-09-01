package com.hewking.develop.util;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.hewking.develop.R;

/**
 * @author: jianhao
 * @create: 2020/8/18
 * @description:
 */
public class TransparentViewManager {

    private View mView;
    private ColorDrawable colorDrawable;
    public static final int MAX_ALPHA = 255, MIN_ALPHA = 0;

    public TransparentViewManager(View mToolbar) {
        this.mView = mToolbar;
        this.colorDrawable = new ColorDrawable(mToolbar.getContext().getResources().getColor(R.color.standard_tab_background_color_night));
    }

    public TransparentViewManager(View mToolbar, ColorDrawable colorDrawable) {
        this.mView = mToolbar;
        this.colorDrawable = colorDrawable;
    }

    //Fading toolbar
    public void manageFadingToolbar(int scrollDistance) {
        if (mView != null && colorDrawable != null) {
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
            mView.setBackground(colorDrawable);
        } else {
            mView.setBackgroundDrawable(colorDrawable);
        }
    }

    public static boolean isSupport(int apiLevel) {
        return Build.VERSION.SDK_INT >= apiLevel;
    }

}
