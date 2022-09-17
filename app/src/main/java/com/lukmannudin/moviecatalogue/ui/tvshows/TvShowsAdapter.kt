package com.lukmannudin.moviecatalogue.ui.tvshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.lukmannudin.moviecatalogue.data.entity.TvShow
import com.lukmannudin.moviecatalogue.databinding.ItemMovieBinding
import com.lukmannudin.moviecatalogue.ui.tvshows.tvshowsdetail.TvShowsDetailActivity
import com.lukmannudin.moviecatalogue.utils.Converters.toStringFormat
import com.lukmannudin.moviecatalogue.utils.setImage

/**
 * Created by Lukmannudin on 5/3/21.
 */


class TvShowsAdapter : PagingDataAdapter<TvShow, TvShowViewHolder>(TvShowCallback()) {

    lateinit var shareCallback: (TvShow) -> Unit
    lateinit var favoriteCallback: (TvShow) -> Unit
    lateinit var onMoveClicked: (Int) -> Unit

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
            item?.let { holder.bind(it, onMoveClicked) }
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, onMoveClicked) }
    }
}