package com.example.albumslistingdemo.data.database

import androidx.room.*


@Dao
interface BookmarkDAO {
    @Insert
    fun insert(bookmarkEntity: BookmarkEntity)

    @Update
    fun update(bookmarkEntity: BookmarkEntity)

    @Delete
    fun delete(bookmarkEntity: BookmarkEntity)

    @Query("SELECT * FROM bookmarkEntity")
    fun getBookmarks(): List<BookmarkEntity>

    @Query("DELETE FROM bookmarkEntity WHERE collectionId = :collectionId")
    fun deleteBookmarkById(collectionId: Int): Int
}