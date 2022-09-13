package com.aditasha.itunesplayer.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aditasha.itunesplayer.player.MediaPlayerFragment
import com.aditasha.itunesplayer.network.RetrofitClient

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java) || modelClass.isAssignableFrom(
                MediaPlayerFragment::class.java
            )
        ) {
            return MainActivityViewModel(RetrofitClient.apiService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}