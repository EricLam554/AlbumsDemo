package com.example.albumsdemo.data

import com.google.gson.annotations.SerializedName

class AlbumsResponse(

    @SerializedName("resultCount")
    var resultCount: Int? = null,

    @SerializedName("results")
    var albumItems: List<AlbumItem>? = null


)