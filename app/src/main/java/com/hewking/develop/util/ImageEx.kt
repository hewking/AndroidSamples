package com.hewking.develop.util

import android.widget.ImageView

internal fun ImageView.load(imgUrl: String) {
    ImageLoader.load(imgUrl,this)
}