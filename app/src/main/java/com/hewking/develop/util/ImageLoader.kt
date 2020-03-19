package com.hewking.develop.util

import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageLoader {

    fun load(imgUrl:String ,target: ImageView) {
        Glide.with(target)
            .load(imgUrl)
            .into(target)
    }

}