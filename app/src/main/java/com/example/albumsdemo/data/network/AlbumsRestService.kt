package com.example.albumsdemo.data.network

import com.example.albumsdemo.data.AlbumsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface AlbumsRestService {

    @GET("search?term=jack+johnson&entity=album")
    suspend fun getAlbums(): AlbumsResponse

}