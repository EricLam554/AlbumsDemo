package com.example.albumsdemo.presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.albumsdemo.data.database.BookmarkDAO
import com.example.albumsdemo.data.database.BookmarkDatabase
import com.example.albumsdemo.databinding.FragmentBookmarkBinding

class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookmarkViewModel: BookmarkViewModel
    private lateinit var bookmarkDatabaseDao: BookmarkDAO

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bookmarkViewModel =
            ViewModelProvider(this).get(BookmarkViewModel::class.java)

        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)

        val root: View = binding.root

        if (container != null) {
            bookmarkDatabaseDao = BookmarkDatabase.getInstance(container.context).bookmarkDAO()
        }

        bookmarkViewModel.getBookmarkList(bookmarkDatabaseDao)
        bookmarkViewModel.getAlbumsList()

        subscribe()

        return root
    }

    private fun subscribe() {
        bookmarkViewModel.albumsListItemViewModelsLiveData.observe(viewLifecycleOwner) {
            val recyclerView = binding.albumsList
            recyclerView.adapter = AlbumsBookmarkListAdapter(it)
            recyclerView.layoutManager = GridLayoutManager(activity, 2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
