package com.android.music.muziko.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.music.muziko.model.Song
import com.android.music.ui.SongsRepository

class RecentlyViewModel : ViewModel(){
    var dataset: MutableLiveData<ArrayList<Any>> = MutableLiveData()
//    lateinit var recRepository: RecentlyRepository
    lateinit var songsRepository: SongsRepository
    init{
        dataset.value = ArrayList()
    }

    fun sendDataToFragment(context: Context){
//        recRepository = RecentlyRepository()
        songsRepository = SongsRepository(context)
        updateData()

    }

    fun updateData() {
//        dataset.value = recRepository.getRecSongs() as ArrayList<Any>
        dataset.value =  songsRepository.getListOfSongs() as ArrayList<Any>
    }

    fun getDataset(): ArrayList<Song>{
        return dataset.value as ArrayList<Song>
    }
}