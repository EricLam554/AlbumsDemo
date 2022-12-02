package com.example.albumsdemo.data.network

import com.example.albumsdemo.data.AlbumsResponse
import com.example.albumsdemo.data.AlbumsResponseDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class AlbumsRestServiceProvider {
    private var restService: AlbumsRestService? = null
    private val gson =
        GsonBuilder().registerTypeAdapter(
            AlbumsResponse::class.java,
            AlbumsResponseDeserializer()
        ).create()

    private var client = OkHttpClient()

    fun getService(): AlbumsRestService? {

        if (restService == null) {

            val baseUrl = "https://itunes.apple.com/"

            restService = Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(AlbumsRestService::class.java)
        }

        return restService
    }

    fun resetService() {
        restService = null
    }

}