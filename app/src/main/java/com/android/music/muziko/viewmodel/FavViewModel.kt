package com.android.music.muziko.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.music.muziko.repository.FavRepository
import com.android.music.ui.Song
import com.android.music.ui.SongsRepository

class FavViewModel: ViewModel() {
    var dataset: MutableLiveData<ArrayList<Any>> = MutableLiveData()
//    var favRepository: FavRepository
    lateinit var songsRepository: SongsRepository

    init{
        dataset.value = ArrayList()

    }
    fun setDataToFragment(context: Context){
//      favRepository = FavRepository()
        songsRepository = SongsRepository(context)
        updateData()
    }

    fun updateData(){
//        dataset.value =  favRepository.getFavSongs() as ArrayList<Any>
        dataset.value =  songsRepository.getListOfSongs() as ArrayList<Any>
    }

    fun getDataset(): ArrayList<Song>{
        return dataset.value as ArrayList<Song>
    }
}