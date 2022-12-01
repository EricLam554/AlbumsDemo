package com.example.albumslistingdemo.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarkEntity")
data class BookmarkEntity(
    @PrimaryKey
    @ColumnInfo(name = "collectionId")
    val collectionId: Int
)