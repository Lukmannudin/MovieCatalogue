package com.lukmannudin.moviecatalogue.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lukmannudin.moviecatalogue.R

/**
 * Created by Lukmannudin on 5/4/21.
 */


fun ImageView.setImage(context: Context, imagePath: String) {
    Glide.with(context)
        .load(imagePath)
        .centerCrop()
        .apply(
            RequestOptions.placeholderOf(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
        )
        .into(this)
}

fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.visible(){
    this.visibility = View.VISIBLE
}

fun View.gone(){
    this.visibility = View.GONE
}