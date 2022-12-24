package com.example.albumsdemo.data

import com.example.albumsdemo.data.datasource.AlbumsDataSource
import com.example.albumsdemo.data.datasource.AlbumsDataSourceRemote

class AlbumsRepository(
    private val albumsDataSourceRemote: AlbumsDataSource,
) {
    suspend fun getAlbums(): Resource<AlbumsResponse?> {
        return albumsDataSourceRemote.getAlbumsData()
    }
}