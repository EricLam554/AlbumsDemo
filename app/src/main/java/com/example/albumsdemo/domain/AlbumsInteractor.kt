package com.example.albumsdemo.domain

import com.example.albumsdemo.data.*

class AlbumsInteractor {
    suspend fun albumsApi(): Resource<AlbumsResponse?>? {
        return AlbumsRepository().getAlbums()
    }
}




