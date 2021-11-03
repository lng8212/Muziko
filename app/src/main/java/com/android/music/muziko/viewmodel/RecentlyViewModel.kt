package com.android.music.muziko.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.music.muziko.model.Song
import com.android.music.muziko.repository.RecentlyRepository
import com.android.music.ui.SongsRepository

class RecentlyViewModel : ViewModel(){
    var dataset: MutableLiveData<ArrayList<Any>> = MutableLiveData()
    var recRepository: RecentlyRepository
    init{
        dataset.value = ArrayList()
        recRepository = RecentlyRepository()
    }

    fun sendDataToFragment(){
        updateData()
    }

    fun updateData() {
        dataset.value = recRepository.getRecSongs() as ArrayList<Any>
    }

    fun getDataset(): ArrayList<Song>{
        return dataset.value as ArrayList<Song>
    }
}