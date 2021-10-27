package com.android.music.muziko.viewmodel

import androidx.lifecycle.MutableLiveData
import com.android.music.muziko.repository.PlaylistPageRepository
import com.android.music.ui.Song

class PlaylistPageViewModel : BaseViewModel(){

    override var dataset: MutableLiveData<ArrayList<Any>> = MutableLiveData()
    lateinit var playlistPageRepository: PlaylistPageRepository
    private var playlistId: Long = -1L

    init {
        dataset.value = ArrayList()
    }

    fun setPlayllistId(pId: Long)
    {
        playlistId = pId
        playlistPageRepository = PlaylistPageRepository(playlistId)
        fillRecyclerView()
    }

    override fun fillRecyclerView() {
        updateDataset()

    }

    override fun updateDataset() {
        dataset.value = playlistPageRepository.getSongs() as ArrayList<Any>
    }

    fun getDataset(): ArrayList<Song>
    {
        return dataset.value as ArrayList<Song>
    }
}