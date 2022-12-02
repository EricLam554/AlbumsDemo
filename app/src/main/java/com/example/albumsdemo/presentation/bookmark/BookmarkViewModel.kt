package com.example.albumsdemo.presentation.bookmark

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.albumsdemo.data.AlbumsResponse
import com.example.albumsdemo.data.AlbumsResponseDeserializer
import com.example.albumsdemo.data.Resource
import com.example.albumsdemo.data.database.BookmarkDAO
import com.example.albumsdemo.data.network.AlbumsRestService
import com.example.albumsdemo.domain.AlbumsInteractor
import com.example.albumsdemo.presentation.home.AlbumsListItemViewModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class BookmarkViewModel : ViewModel() {

    private val resultCount = MutableLiveData<Int?>()
    val albumsListItemViewModelsLiveData = MutableLiveData<ArrayList<AlbumsListItemViewModel>>()
    private val albumsInteractor = AlbumsInteractor()

    private var bookmarkArray: List<Int>? = null

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
                bookmarkArray?.let { bookmarks ->

                    if (bookmarks.contains(it.collectionId)) {
                        val albumsListItemViewModel = AlbumsListItemViewModel()
                        albumsListItemViewModel.collectionId = it.collectionId ?: 0
                        albumsListItemViewModel.collectionName = it.collectionName ?: ""
                        albumsListItemViewModel.artistName = it.artistName ?: ""
                        albumsListItemViewModel.price = "$ " + it.collectionPrice ?: ""
                        albumsListItemViewModel.imageUrl = it.artworkUrl100
                        albumsListItemViewModel.isBookmarked = bookmarks.contains(albumsListItemViewModel.collectionId)

                        albumsListItemViewModels.add(albumsListItemViewModel)
                    }
                }

            }

            albumsListItemViewModelsLiveData.value = albumsListItemViewModels
        }
    }

    private fun onDataNotAvailable(throwable: Throwable?) {
        Timber.d("load data: onDataNotAvailable %s", throwable)
    }

    fun getBookmarkList(bookmarkDatabaseDao: BookmarkDAO) {
        CoroutineScope(Dispatchers.IO).launch {
            bookmarkArray = bookmarkDatabaseDao.getBookmarks().map {
                it.collectionId
            }
        }
    }

}