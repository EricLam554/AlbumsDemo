package com.example.albumsdemo.presentation.home

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.albumsdemo.data.AlbumsResponse
import com.example.albumsdemo.data.AlbumsResponseDeserializer
import com.example.albumsdemo.data.Resource
import com.example.albumsdemo.data.database.BookmarkDAO
import com.example.albumsdemo.data.database.BookmarkDatabase
import com.example.albumsdemo.data.network.AlbumsRestService
import com.example.albumsdemo.domain.AlbumsInteractor
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val albumsInteractor: AlbumsInteractor,
    private val bookmarkDatabase: BookmarkDatabase
) : ViewModel() {

    private val resultCount = MutableLiveData<Int?>()
    val albumsListItemViewModelsLiveData = MutableLiveData<ArrayList<AlbumsListItemViewModel>>()

    var bookmarkArray: List<Int>? = null

    public var bookmarkDatabaseDAO = bookmarkDatabase.bookmarkDAO()

    var listStateParcel: Parcelable? = null

    fun saveListState(parcel: Parcelable) {
        listStateParcel = parcel
    }

    fun getAlbumsList() {
        viewModelScope.launch {
            try {
                val response = albumsInteractor.albumsApi()

                if (response is Resource.Success) {
                    mapData(response.data)
                }else{
                    onDataNotAvailable(response?.throwable)
                }
            }catch(e: Exception) {
                onDataNotAvailable(e)
            }
        }
    }

    private fun mapData(data: AlbumsResponse?) {
        Timber.d("load data: " + data.toString())

        val albumsListItemViewModels = ArrayList<AlbumsListItemViewModel>()

        data?.let { response ->
            resultCount.value = response.resultCount

            data.albumItems?.map { it ->
                val albumsListItemViewModel = AlbumsListItemViewModel()
                albumsListItemViewModel.collectionId = it.collectionId ?: 0
                albumsListItemViewModel.collectionName = it.collectionName ?: ""
                albumsListItemViewModel.artistName = it.artistName ?: ""
                albumsListItemViewModel.price = "$ " + it.collectionPrice ?: ""
                albumsListItemViewModel.imageUrl = it.artworkUrl100

                bookmarkArray?.let { bookmarks ->
                    albumsListItemViewModel.isBookmarked = bookmarks.contains(albumsListItemViewModel.collectionId)
                }

                albumsListItemViewModels.add(albumsListItemViewModel)
            }

            albumsListItemViewModelsLiveData.value = albumsListItemViewModels
        }
    }

    private fun onDataNotAvailable(throwable: Throwable?) {
        Timber.d("load data: onDataNotAvailable %s", throwable)
    }

     fun getBookmarkList() {
         CoroutineScope(Dispatchers.IO).launch {
            bookmarkArray = bookmarkDatabaseDAO
                .getBookmarks().map {
                it.collectionId
            }
         }
    }

}