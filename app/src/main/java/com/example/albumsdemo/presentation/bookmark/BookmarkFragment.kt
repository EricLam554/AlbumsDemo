package com.example.albumsdemo.presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.albumsdemo.data.database.BookmarkDAO
import com.example.albumsdemo.data.database.BookmarkDatabase
import com.example.albumsdemo.databinding.FragmentBookmarkBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private val bookmarkViewModel by viewModels<BookmarkViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)

        val root: View = binding.root

        bookmarkViewModel.getBookmarkList()
        bookmarkViewModel.getAlbumsList()

        subscribe()

        return root
    }

    private fun subscribe() {
        bookmarkViewModel.albumsListItemViewModelsLiveData.observe(viewLifecycleOwner) {
            val recyclerView = binding.albumsList
            recyclerView.adapter = AlbumsBookmarkListAdapter(it, bookmarkViewModel.bookmarkDatabaseDAO)
            recyclerView.layoutManager = GridLayoutManager(activity, 2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
