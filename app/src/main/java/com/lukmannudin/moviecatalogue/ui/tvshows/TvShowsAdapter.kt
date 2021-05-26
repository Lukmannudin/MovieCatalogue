package com.lukmannudin.moviecatalogue.ui.tvshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import com.lukmannudin.moviecatalogue.databinding.ItemMovieBinding
import com.lukmannudin.moviecatalogue.ui.tvshowsdetail.TvShowsDetailActivity
import com.lukmannudin.moviecatalogue.utils.Converters.toStringFormat
import com.lukmannudin.moviecatalogue.utils.setImage

/**
 * Created by Lukmannudin on 5/3/21.
 */


class TvShowsAdapter : PagingDataAdapter<TvShow, TvShowsAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    lateinit var shareCallback: (TvShow) -> Unit
    lateinit var favoriteCallback: (TvShow) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemsAcademyBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemsAcademyBinding)
    }

    override fun onBindViewHolder(
        holder: TvShowViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val item = getItem(position)
            item?.let { holder.bind(it, shareCallback) }
        } else {
            onBindViewHolder(holder, position)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShow>() {
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
    }

    inner class TvShowViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShow, shareCallback: (TvShow) -> Unit) {
            with(binding) {
                tvItemTitle.text = tvShow.title
                tvItemDate.text = tvShow.releaseDate?.toStringFormat()
                tvItemOverview.text = tvShow.overview
                ivPoster.setImage(itemView.context, tvShow.posterPath)
                cbFavorite.isChecked = tvShow.isFavorite

                ivShare.setOnClickListener {
                    shareCallback.invoke(tvShow)
                }

                itemView.setOnClickListener {
                    TvShowsDetailActivity.start(itemView.context, tvShow.id)
                }

                cbFavorite.setOnClickListener {
                    tvShow.isFavorite = cbFavorite.isChecked
                    favoriteCallback.invoke(tvShow)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, shareCallback) }
    }
}