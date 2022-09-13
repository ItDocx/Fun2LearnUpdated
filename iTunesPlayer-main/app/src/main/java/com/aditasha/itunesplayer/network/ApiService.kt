package com.aditasha.itunesplayer.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    suspend fun getMusicList(
        @Query("term") term: String,
        @Query("country") country: String,
        @Query("media") media: String = MUSIC,
        @Query("entity") entity: String = SONG,
        @Query("attribute") attribute: String = ARTIST
    ): ApiResponse

    companion object {
        const val MUSIC = "music"
        const val SONG = "song"
        const val ARTIST = "artistTerm"
    }
}