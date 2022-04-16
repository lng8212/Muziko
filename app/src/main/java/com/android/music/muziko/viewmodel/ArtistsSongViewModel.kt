package com.android.music.muziko.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.music.muziko.model.Song
import com.android.music.ui.SongsRepository


class ArtistsSongViewModel: BaseViewModel(){
    lateinit var songsRepository: SongsRepository

    init{
        dataset = MutableLiveData()
    }

    override fun sendDataToFragment(context: Context?, artist: String?){
        songsRepository = SongsRepository(context!!)
        updateData(artist)
    }

    override fun updateData(data: String?) {
        dataset.value =  songsRepository.getSongOfArtists(data!!)
    }


    override fun getDataset(): ArrayList<Song>{
        return dataset.value as ArrayList<Song>
    }
}