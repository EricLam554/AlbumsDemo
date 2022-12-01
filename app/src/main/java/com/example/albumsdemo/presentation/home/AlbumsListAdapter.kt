package com.example.albumsdemo.presentation.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.albumsdemo.data.database.BookmarkDatabase
import com.example.albumsdemo.data.database.BookmarkEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AlbumsListAdapter(private val viewModels: ArrayList<AlbumsListItemViewModel>) :
    RecyclerView.Adapter<AlbumsListViewHolder>() {

    override fun getItemCount() = viewModels.size

    override fun onBindViewHolder(holder: AlbumsListViewHolder, position: Int) {
        holder.bind(viewModels[position])

        holder.binding.albumsCard.setOnClickListener {
            val bookmarkDatabaseDao = BookmarkDatabase.getInstance(it.context).bookmarkDAO()

            if (viewModels[position].isBookmarked) {
                viewModels[position].isBookmarked = false

                CoroutineScope(Dispatchers.IO).launch {
                    bookmarkDatabaseDao.deleteBookmarkById(viewModels[position].collectionId)
                }

            }else{
                viewModels[position].isBookmarked = true

                CoroutineScope(Dispatchers.IO).launch {
                    bookmarkDatabaseDao.insert(BookmarkEntity(viewModels[position].collectionId))
                }
            }

            notifyDataSetChanged()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsListViewHolder {
        return AlbumsListViewHolder.from(parent)
    }



}