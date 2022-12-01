package com.example.albumsdemo.domain

import androidx.lifecycle.viewModelScope
import com.example.albumsdemo.data.*
import com.example.albumsdemo.data.network.AlbumsRestService
import com.example.albumsdemo.data.network.AlbumsRestServiceProvider
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

 class AlbumsInteractor {
     fun albumsApi(): Call<AlbumsResponse>? {
        return AlbumsRestServiceProvider().getService()?.getAlbums()
    }
}

