package com.example.albumsdemo.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class AlbumsListItemViewModel : ViewModel() {

    var collectionId = 0
    var imageUrl: String? = null
    var collectionName = ""
    var artistName = ""
    var price = ""
    var isBookmarked = false

}