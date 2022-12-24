package com.example.albumsdemo.dependency_injection

import android.content.Context
import androidx.room.Room
import com.example.albumsdemo.data.AlbumsRepository
import com.example.albumsdemo.data.database.BookmarkDatabase
import com.example.albumsdemo.domain.AlbumsInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // USER CASE
    @Singleton
    @Provides
    fun provideAlbumsInteractor(albumsRepository: AlbumsRepository): AlbumsInteractor {
        return AlbumsInteractor(albumsRepository)
    }

    // DATABASE
    @Singleton
    @Provides
    fun provideBookmarkDatabase(@ApplicationContext context: Context): BookmarkDatabase {
        return Room.databaseBuilder(context, BookmarkDatabase::class.java, "BookmarkDatabase")
            .fallbackToDestructiveMigration()
            .build()
    }






}