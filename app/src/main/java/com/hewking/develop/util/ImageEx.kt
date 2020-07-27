package com.hewking.develop.util

import android.widget.ImageView
import androidx.annotation.DrawableRes

internal fun ImageView.load(imgUrl: String) {
    ImageLoader.load(imgUrl,this)
}

internal fun ImageView.load(@DrawableRes res: Int) {
    ImageLoader.load(res,this)
}