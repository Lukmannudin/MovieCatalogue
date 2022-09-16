package com.lukmannudin.moviecatalogue.ui.tvshows

import androidx.recyclerview.widget.RecyclerView
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import com.lukmannudin.moviecatalogue.databinding.ItemMovieBinding
import com.lukmannudin.moviecatalogue.ui.tvshows.tvshowsdetail.TvShowsDetailActivity
import com.lukmannudin.moviecatalogue.utils.setImage

class TvShowViewHolder(private val binding: ItemMovieBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(tvShow: TvShow, shareCallback: (TvShow) -> Unit) {
        with(binding) {
            ivPoster.setImage(itemView.context, tvShow.posterPath)
            itemView.setOnClickListener {
                TvShowsDetailActivity.start(itemView.context, tvShow.id)
            }
        }
    }
}