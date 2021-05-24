package com.lukmannudin.moviecatalogue.ui.tvshows

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.lukmannudin.moviecatalogue.utils.NetworkStateItemViewHolder

/**
 * Created by Lukmannudin on 20/05/21.
 */
class PostsLoadStateAdapter(
    private val adapter: TvShowsAdapter
) : LoadStateAdapter<NetworkStateItemViewHolder>() {
    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        return NetworkStateItemViewHolder(parent) { adapter.retry() }
    }
}