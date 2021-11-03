package com.android.music.muziko.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.music.muziko.model.Song
import com.android.music.muziko.repository.FavRepository
import com.android.music.ui.SongsRepository

class FavViewModel: ViewModel() {
    var dataset: MutableLiveData<ArrayList<Any>> = MutableLiveData()
    var favRepository: FavRepository


    init{
        dataset.value = ArrayList()
        favRepository = FavRepository()

    }
    fun setDataToFragment(){
        updateData()
    }

    fun updateData(){
        dataset.value =  favRepository.getFavSongs() as ArrayList<Any>
    }

    fun getDataset(): ArrayList<Song>{
        return dataset.value as ArrayList<Song>
    }
}