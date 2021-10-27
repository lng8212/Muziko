package com.android.music.muziko.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.music.muziko.model.Playlist
import com.android.music.muziko.repository.PlaylistRepository

class PlaylistViewModel : BaseViewModel(){

    override var dataset: MutableLiveData<ArrayList<Any>> = MutableLiveData()
    lateinit var playlistRepository: PlaylistRepository


    init {
        dataset.value = ArrayList()
    }

    fun setFragmentContext(context: Context) {
        playlistRepository = PlaylistRepository(context)
        fillRecyclerView()
    }

    override fun fillRecyclerView() {
        updateDataset()
    }

    override fun updateDataset() {
        dataset.value = playlistRepository.getPlaylists()!! as ArrayList<Any>
    }

    fun getDataSet(): ArrayList<Playlist> {
        updateDataset()
        return dataset.value as ArrayList<Playlist>
    }
}