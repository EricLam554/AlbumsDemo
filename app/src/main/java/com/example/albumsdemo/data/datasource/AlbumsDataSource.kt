package com.example.albumsdemo.data.datasource

import com.example.albumsdemo.data.AlbumsResponse
import com.example.albumsdemo.data.Resource

interface AlbumsDataSource {
    suspend fun getAlbumsData(): Resource<AlbumsResponse?>
}