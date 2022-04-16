package com.android.music.muziko.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.music.muziko.model.Playlist
import com.android.music.muziko.repository.PlaylistRepository
import com.android.music.muziko.repository.RoomRepository

class PlaylistViewModel : BaseViewModel(){

    lateinit var playlistRepository: PlaylistRepository

    init {
        dataset = MutableLiveData()
    }

    fun setFragmentContext(context: Context) {
        playlistRepository = PlaylistRepository(context)
        updateData()
    }

    override fun sendDataToFragment(context: Context?, artist: String?) {

    }

    override fun updateData(data: String?) {
        dataset.value = playlistRepository.getPlaylists()
    }

    override fun getDataset(): ArrayList<*> {
        updateData()
        return dataset.value as ArrayList<Playlist>
    }
}