package com.hewking.develop.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import com.bumptech.glide.Glide

object ImageLoader {

    fun load(imgUrl:String ,target: ImageView) {
        Glide.with(target)
            .load(imgUrl)
            .into(target)
    }

    fun load(@DrawableRes res: Int, target: ImageView) {
        Glide.with(target)
            .load(res)
            .into(target)
    }

}