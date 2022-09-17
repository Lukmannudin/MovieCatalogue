package com.lukmannudin.moviecatalogue.utils

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.lukmannudin.moviecatalogue.R

/**
 * Created by Lukmannudin on 5/4/21.
 */


fun ImageView.setImage(context: Context, imagePath: String, isRounded: Boolean = true) {
    val requestOptions = if (isRounded) {
        RequestOptions.placeholderOf(R.drawable.ic_loading)
            .error(R.drawable.ic_error).transform(CenterCrop(), RoundedCorners(16))
    } else {
        RequestOptions.placeholderOf(R.drawable.ic_loading)
            .error(R.drawable.ic_error).transform(CenterCrop())
    }

    Glide.with(context)
        .load(imagePath)
        .apply(requestOptions)
        .into(this)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}