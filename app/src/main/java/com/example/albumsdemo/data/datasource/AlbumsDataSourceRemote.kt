package com.example.albumsdemo.data.datasource

import com.example.albumsdemo.data.AlbumsResponse
import com.example.albumsdemo.data.Resource
import com.example.albumsdemo.data.network.AlbumsRestServiceProvider
import com.example.albumsdemo.data.network.SafeApiCall

class AlbumsDataSourceRemote: AlbumsDataSource {
    override suspend fun getAlbumsData(): Resource<AlbumsResponse?> {

        val service = AlbumsRestServiceProvider().getService()
            ?: return Resource.OtherError(Throwable("Service not found"))

        return when (
            val result = SafeApiCall {
                service.getAlbums()
            }
        ) {
            is Resource.Success -> Resource.Success(result.data!!)
            is Resource.HttpError -> Resource.HttpError(result.code, result.throwable)
            is Resource.OtherError -> Resource.OtherError(result.throwable)
        }

    }

}