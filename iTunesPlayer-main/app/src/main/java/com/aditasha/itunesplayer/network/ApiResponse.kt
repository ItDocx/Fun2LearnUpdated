package com.aditasha.itunesplayer.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiResponse(

    @field:SerializedName("resultCount")
    val resultCount: Int? = null,

    @field:SerializedName("results")
    val results: List<ResultsItem?>? = null
) : Parcelable

@Parcelize
data class ResultsItem(

    @field:SerializedName("trackId")
    val trackId: Long? = null,

    @field:SerializedName("artworkUrl100")
    val artworkUrl100: String? = null,

    @field:SerializedName("trackTimeMillis")
    val trackTimeMillis: Int? = null,

    @field:SerializedName("previewUrl")
    val previewUrl: String? = null,

    @field:SerializedName("trackName")
    val trackName: String? = null,

    @field:SerializedName("collectionName")
    val collectionName: String? = null,

    @field:SerializedName("artistName")
    val artistName: String? = null
) : Parcelable

