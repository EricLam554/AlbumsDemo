package com.example.albumsdemo.domain

import com.example.albumsdemo.data.*

class AlbumsInteractor(
    private val albumsRepository: AlbumsRepository
) {
    suspend fun albumsApi(): Resource<AlbumsResponse?>? {
        return albumsRepository.getAlbums()
    }
}




