package com.example.albumsdemo.dependency_injection

import com.example.albumsdemo.data.AlbumsRepository
import com.example.albumsdemo.data.datasource.AlbumsDataSource
import com.example.albumsdemo.data.datasource.AlbumsDataSourceRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class RemoteAlbumsDataSource

    @Singleton
    @RemoteAlbumsDataSource
    @Provides
    fun provideAlbumsDataSourceRemote(): AlbumsDataSource {
        return AlbumsDataSourceRemote()
    }

    @Singleton
    @Provides
    fun provideAlbumsRepository(
        @NetworkModule.RemoteAlbumsDataSource albumsDataSourceRemote: AlbumsDataSource
    ): AlbumsRepository {
        return AlbumsRepository(
            albumsDataSourceRemote
        )
    }
}
