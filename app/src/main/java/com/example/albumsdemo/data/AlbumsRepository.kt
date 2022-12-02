package com.example.albumsdemo.data

import com.example.albumsdemo.data.datasource.AlbumsDataSourceRemote

class AlbumsRepository {
    suspend fun getAlbums(): Resource<AlbumsResponse?> {
        return AlbumsDataSourceRemote().getAlbumsData()
    }
}