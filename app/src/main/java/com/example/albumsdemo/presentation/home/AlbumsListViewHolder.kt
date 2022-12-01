package com.example.albumsdemo.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.albumsdemo.databinding.ItemAlbumHolderBinding


class AlbumsListViewHolder private constructor(val binding: ItemAlbumHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(viewModel: AlbumsListItemViewModel) {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): AlbumsListViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemAlbumHolderBinding.inflate(layoutInflater, parent, false)

            return AlbumsListViewHolder(binding)
        }
    }
}