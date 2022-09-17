package com.lukmannudin.moviecatalogue.ui.tvshows

import androidx.recyclerview.widget.DiffUtil
import com.lukmannudin.moviecatalogue.data.entity.TvShow

class TvShowCallback : DiffUtil.ItemCallback<TvShow>() {
    override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: TvShow, newItem: TvShow): Any {
        return oldItem.id == newItem.id
    }
}