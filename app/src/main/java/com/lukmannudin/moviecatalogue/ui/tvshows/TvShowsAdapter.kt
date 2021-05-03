package com.lukmannudin.moviecatalogue.ui.tvshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lukmannudin.moviecatalogue.data.TvShow
import com.lukmannudin.moviecatalogue.databinding.ItemTvshowBinding
import com.lukmannudin.moviecatalogue.loadImage

/**
 * Created by Lukmannudin on 5/3/21.
 */


class TvShowsAdapter : RecyclerView.Adapter<TvShowsAdapter.MoviesViewHolder>() {
    private var tvShows = ArrayList<TvShow>()

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
        holder.bind(tvShow)
    }

    override fun getItemCount(): Int = tvShows.size


    class MoviesViewHolder(private val binding: ItemTvshowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShow) {
            with(binding) {
                tvItemTitle.text = tvShow.title
                tvItemDate.text = tvShow.releaseDate

                imgPoster.loadImage(itemView.context, tvShow.posterPath)

//                itemView.setOnClickListener {
//                    val intent = Intent(itemView.context, DetailCourseActivity::class.java)
//                    intent.putExtra(DetailCourseActivity.EXTRA_COURSE, course.courseId)
//                    itemView.context.startActivity(intent)
//                }
//                Glide.with(itemView.context)
//                    .load(course.imagePath)
//                    .apply(
//                        RequestOptions.placeholderOf(R.drawable.ic_loading)
//                            .error(R.drawable.ic_error))
//                    .into(imgPoster)
            }
        }
    }
}