package com.example.albumsdemo.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.albumsdemo.databinding.FragmentAlbumsDetailBinding
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class AlbumsDetailFragment : Fragment() {

    private var _binding: FragmentAlbumsDetailBinding? = null
    private val binding get() = _binding!!

    private val albumsDetailViewModel by viewModels<AlbumsDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumsDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = albumsDetailViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        albumsDetailViewModel.collectionId.postValue(arguments?.getInt("collectionId"))
        albumsDetailViewModel.collectionName.postValue(arguments?.getString("collectionName"))
        albumsDetailViewModel.collectionImageUrl.postValue(arguments?.getString("collectionImageUrl"))

        binding.backBtn.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}