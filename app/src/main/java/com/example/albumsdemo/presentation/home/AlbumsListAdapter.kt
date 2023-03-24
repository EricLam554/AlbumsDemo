package com.example.albumsdemo.presentation.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.albumsdemo.data.database.BookmarkDAO
import com.example.albumsdemo.data.database.BookmarkDatabase
import com.example.albumsdemo.data.database.BookmarkEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AlbumsListAdapter(
    private val viewModels: ArrayList<AlbumsListItemViewModel>,
    private val bookmarkDatabaseDao: BookmarkDAO
) :
    RecyclerView.Adapter<AlbumsListViewHolder>() {

    var onItemClick: ((AlbumsListItemViewModel) -> Unit)? = null

    override fun getItemCount() = viewModels.size

    override fun onBindViewHolder(holder: AlbumsListViewHolder, position: Int) {
        holder.bind(viewModels[position])

        val tempCollectionId = viewModels[position].collectionId

        holder.binding.albumsCard.setOnClickListener {
            onItemClick?.invoke(viewModels[position])
        }

        holder.binding.albumsCard.setOnLongClickListener {
            if (viewModels[position].isBookmarked) {
                viewModels[position].isBookmarked = false

                CoroutineScope(Dispatchers.IO).launch {
                    bookmarkDatabaseDao.deleteBookmarkById(tempCollectionId)
                }
            }else{
                viewModels[position].isBookmarked = true

                CoroutineScope(Dispatchers.IO).launch {
                    bookmarkDatabaseDao.insert(BookmarkEntity(viewModels[position].collectionId))
                }
            }

            notifyDataSetChanged()

            return@setOnLongClickListener true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsListViewHolder {
        return AlbumsListViewHolder.from(parent)
    }


}