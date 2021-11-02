package com.android.music.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.music.muziko.model.Song

class SongViewModel : ViewModel() {
    var liveListSong: MutableLiveData<ArrayList<Song>> = MutableLiveData()
    lateinit var songsRepository: SongsRepository

    init {
        liveListSong.value = ArrayList()
    }
    fun setDataToFragment(context: Context){
        songsRepository = SongsRepository(context)
        updateData()
    }

    fun updateData() {
        liveListSong.value = songsRepository.getListOfSongs()
    }
    fun getData(): ArrayList<Song> {
        return liveListSong.value as ArrayList<Song>
    }
}