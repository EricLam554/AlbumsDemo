package com.example.albumsdemo.data

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class AlbumsResponseDeserializer : JsonDeserializer<AlbumsResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): AlbumsResponse {
        val jsonObject: JsonObject = json?.asJsonObject!!
        val jsonArray = jsonObject.get("results").asJsonArray
        val albumsResponse = AlbumsResponse()
        val albumItems: MutableList<AlbumItem> = mutableListOf()

        albumsResponse.resultCount = jsonObject.get("resultCount").asInt

        jsonArray.forEach { albumItem ->
            val albumItemElement = albumItem.asJsonObject

            val albumItem = AlbumItem(
                albumItemElement.get("artistName").asString,
                albumItemElement.get("artworkUrl100").asString,
                albumItemElement.get("collectionId").asInt,
                albumItemElement.get("collectionName").asString,
                albumItemElement.get("collectionPrice").asDouble,
                albumItemElement.get("collectionViewUrl").asString
            )

            albumItems.add(albumItem)
        }
        albumsResponse.albumItems = albumItems
        return albumsResponse
    }
}