package com.hewking.develop.util;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * author:  ycl
 * date:  2019/08/20 15:24
 * desc:
 */
public class StateBarUtils {

    public static void topMarginStateBarHeight(Context context, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                LinearLayout.LayoutParams params = ((LinearLayout.LayoutParams) layoutParams);
                params.topMargin += DensityUtil.getStatusBarHeight(context);
                view.setLayoutParams(params);
            }
        }
    }

    public static void paddingTopStateBarHeight(Context context, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setPadding(view.getPaddingLeft(),
                    view.getPaddingTop() + DensityUtil.getStatusBarHeight(context),
                    view.getPaddingRight(),
                    view.getPaddingBottom());

            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT || layoutParams.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                return;
            }
            layoutParams.height += DensityUtil.getStatusBarHeight(context);
            view.setLayoutParams(layoutParams);
        }
    }

    public static void paddingTopStatusBar(Context context, View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setPadding(view.getPaddingLeft(),
                    view.getPaddingTop() + DensityUtil.getStatusBarHeight(context),
                    view.getPaddingRight(),
                    view.getPaddingBottom());
        }
    }

    public static void clearPaddingTopStatusBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setPadding(view.getPaddingLeft(),
                    view.getPaddingTop(),
                    view.getPaddingRight(),
                    view.getPaddingBottom());
        }
    }
}
