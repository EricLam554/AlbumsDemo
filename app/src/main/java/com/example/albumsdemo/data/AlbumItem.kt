package com.example.albumsdemo.data

import com.google.gson.annotations.SerializedName

class AlbumItem(

    @SerializedName("artistName")
    val artistName: String? = null,

    @SerializedName("artworkUrl100")
    val artworkUrl100: String? = null,

    @SerializedName("collectionId")
    val collectionId: Int? = null,

    @SerializedName("collectionName")
    val collectionName: String? = null,

    @SerializedName("collectionPrice")
    val collectionPrice: Double? = null,

    @SerializedName("collectionViewUrl")
    val collectionViewUrl: String? = null,

)