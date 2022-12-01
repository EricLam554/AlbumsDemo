package com.example.albumslistingdemo.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.albumslistingdemo.presentation.home.HomeFragment


@Database(entities = [BookmarkEntity::class], version = 1)
abstract class BookmarkDatabase : RoomDatabase() {

    companion object {
        private var instance: BookmarkDatabase? = null
        private var DB_NAME = "BookmarkDatabase"

        fun getInstance(context: Context): BookmarkDatabase {
            return instance ?: Room.databaseBuilder(context, BookmarkDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build().also { instance = it }
        }
    }

    abstract fun bookmarkDAO(): BookmarkDAO

}
