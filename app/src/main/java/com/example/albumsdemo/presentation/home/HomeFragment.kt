package com.example.albumsdemo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.albumsdemo.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_bookmark.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root: View = binding.root

        if (homeViewModel.listStateParcel == null) {
            homeViewModel.getBookmarkList()
            homeViewModel.getAlbumsList()
        }

        subscribe()

        return root
    }

    private fun subscribe() {
        homeViewModel.albumsListItemViewModelsLiveData.observe(viewLifecycleOwner) {
            val recyclerView = binding.albumsList
            recyclerView.adapter = AlbumsListAdapter(it, homeViewModel.bookmarkDatabaseDAO)
            recyclerView.layoutManager = GridLayoutManager(activity, 2)

            homeViewModel.listStateParcel?.let { parcelable ->
                recyclerView.layoutManager?.onRestoreInstanceState(parcelable)
                homeViewModel.listStateParcel = null
            }
        }
    }

    override fun onDestroyView() {
        val listState = binding.albumsList?.layoutManager?.onSaveInstanceState()
        listState?.let {
            homeViewModel.saveListState(it)
        }
        super.onDestroyView()

        _binding = null
    }


}