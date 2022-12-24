package com.example.albumsdemo.presentation.bookmark

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.albumsdemo.data.database.BookmarkDAO
import com.example.albumsdemo.data.database.BookmarkDatabase
import com.example.albumsdemo.presentation.home.AlbumsListItemViewModel
import com.example.albumsdemo.presentation.home.AlbumsListViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AlbumsBookmarkListAdapter(
    private val viewModels: ArrayList<AlbumsListItemViewModel>,
    private val bookmarkDatabaseDao: BookmarkDAO
    ) :
    RecyclerView.Adapter<AlbumsListViewHolder>() {

    override fun getItemCount() = viewModels.size

    override fun onBindViewHolder(holder: AlbumsListViewHolder, position: Int) {
        holder.bind(viewModels[position])

        holder.binding.albumsCard.setOnLongClickListener {

            if (viewModels[position].isBookmarked) {
                viewModels[position].isBookmarked = false
                val tempCollectionId = viewModels[position].collectionId

                viewModels.removeAt(position)

                CoroutineScope(Dispatchers.IO).launch {
                    bookmarkDatabaseDao.deleteBookmarkById(tempCollectionId)
                }

                notifyItemRemoved(position)
                notifyItemRangeChanged(position, viewModels.size)
            }

            return@setOnLongClickListener true
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsListViewHolder {
        return AlbumsListViewHolder.from(parent)
    }


}