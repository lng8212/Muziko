package com.android.music.muziko.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.music.muziko.model.Song
import com.android.music.ui.SongsRepository


class ArtistsSongViewModel: ViewModel(){
    var dataset: MutableLiveData<ArrayList<Song>> = MutableLiveData()
    lateinit var songsRepository: SongsRepository

    init{
        dataset.value = ArrayList()
    }

    fun sendDataToFragment(context: Context, artist: String){
        songsRepository = SongsRepository(context)
        updateData(artist)
    }

    fun updateData(artist: String) {
        dataset.value =  songsRepository.getSongOfArtists(artist)
    }

    fun getDataset(): ArrayList<Song>{
        return dataset.value as ArrayList<Song>
    }
}