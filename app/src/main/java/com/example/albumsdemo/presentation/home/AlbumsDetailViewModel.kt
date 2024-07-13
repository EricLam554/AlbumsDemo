package com.example.albumsdemo.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AlbumsDetailViewModel @Inject constructor(): ViewModel() {

    val collectionId = MutableLiveData<Int?>()
    val collectionName = MutableLiveData<String?>()

}