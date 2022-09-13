package com.aditasha.itunesplayer.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditasha.itunesplayer.network.ApiService
import com.aditasha.itunesplayer.network.ResultsItem
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivityViewModel(private val apiService: ApiService) : ViewModel() {

    private var _listMusic = MutableStateFlow(ArrayList<ResultsItem>())
    private var _position = MutableSharedFlow<Int>(0, 0, BufferOverflow.SUSPEND)
    private var _playingMusic = MutableSharedFlow<Int>(0, 0, BufferOverflow.SUSPEND)

    val listMusic = _listMusic.asStateFlow()
    val position = _position.asSharedFlow()
    val playingMusic = _playingMusic.asSharedFlow()

    fun getMusicList(term: String, country: String): Flow<State<ArrayList<ResultsItem>>> = flow {
        emit(State.Loading())
        try {
            val response = apiService.getMusicList(term, country)
            val musicData = response.results
            val arrayList: ArrayList<ResultsItem> = ArrayList()

            if (musicData != null) {
                for (music in musicData) {
                    music?.apply {
                        arrayList.add(
                            ResultsItem(
                                trackId,
                                artworkUrl100,
                                trackTimeMillis,
                                previewUrl,
                                trackName,
                                collectionName,
                                artistName
                            )
                        )
                    }
                }
            }

            _listMusic.emit(arrayList)
            emit(State.Success(arrayList))

        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }
    }

    fun addPlayingMusic(pos: Int) {
        viewModelScope.launch { _playingMusic.emit(pos) }
    }

    fun addPlayingPosition(pos: Int) {
        viewModelScope.launch { _position.emit(pos) }
    }
}