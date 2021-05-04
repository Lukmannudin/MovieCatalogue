package com.lukmannudin.moviecatalogue.ui.tvshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukmannudin.moviecatalogue.data.TvShow
import com.lukmannudin.moviecatalogue.databinding.ItemTvshowBinding
import com.lukmannudin.moviecatalogue.utils.setImage
import com.lukmannudin.moviecatalogue.ui.tvshowsdetail.TvShowsDetailActivity

/**
 * Created by Lukmannudin on 5/3/21.
 */


class TvShowsAdapter : RecyclerView.Adapter<TvShowsAdapter.MoviesViewHolder>() {

    private var tvShows = ArrayList<TvShow>()

    lateinit var shareCallback: (TvShow) -> Unit

    fun setTvShows(tvShows: List<TvShow>?) {
        if (tvShows == null) return
        this.tvShows.clear()
        this.tvShows.addAll(tvShows)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemTvshowBinding = ItemTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemTvshowBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val tvShow = tvShows[position]
        holder.bind(tvShow, shareCallback)
    }

    override fun getItemCount(): Int = tvShows.size

    class MoviesViewHolder(private val binding: ItemTvshowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShow, shareCallback: (TvShow) -> Unit) {
            with(binding) {
                tvItemTitle.text = tvShow.title
                tvItemDate.text = tvShow.releaseDate

                ivPoster.setImage(itemView.context, tvShow.posterPath)

                ivShare.setOnClickListener {
                    shareCallback.invoke(tvShow)
                }

                itemView.setOnClickListener {
                    TvShowsDetailActivity.start(itemView.context, tvShow)
                }
            }
        }
    }
}