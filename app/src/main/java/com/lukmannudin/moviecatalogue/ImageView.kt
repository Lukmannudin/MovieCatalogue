package com.lukmannudin.moviecatalogue

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Lukmannudin on 5/4/21.
 */


fun ImageView.loadImage(context: Context, imagePath: String) {
    Glide.with(context)
        .load(imagePath)
        .apply(
            RequestOptions.placeholderOf(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
        )
        .into(this)
}