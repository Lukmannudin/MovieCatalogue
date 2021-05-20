package com.lukmannudin.moviecatalogue.ui.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Lukmannudin on 20/05/21.
 */
class LoaderStateAdapter :
    LoadStateAdapter<LoaderStateAdapter.ViewHolder>() {

    lateinit var loadStateCallBack: (LoadState) -> Unit

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        loadStateCallBack.invoke(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                android.R.layout.simple_list_item_1,
                parent,
                false
            )
        )
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}