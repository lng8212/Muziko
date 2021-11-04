package com.android.music.muziko.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.music.muziko.model.Playlist
import com.android.music.muziko.repository.PlaylistRepository
import com.android.music.muziko.repository.RoomRepository

class PlaylistViewModel : ViewModel(){

    var dataset: MutableLiveData<ArrayList<Playlist>> = MutableLiveData()
    lateinit var playlistRepository: PlaylistRepository

    init {
        dataset.value = ArrayList()
    }

    fun setFragmentContext(context: Context) {
        playlistRepository = PlaylistRepository(context)
        updateDataset()
    }

    fun updateDataset() {
        dataset.value = playlistRepository.getPlaylists()
    }

    fun getDataSet(): ArrayList<Playlist> {
        updateDataset()
        return dataset.value as ArrayList<Playlist>
    }
}