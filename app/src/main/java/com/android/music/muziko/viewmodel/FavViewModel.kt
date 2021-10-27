package com.android.music.muziko.viewmodel

import androidx.lifecycle.MutableLiveData
import com.android.music.muziko.repository.FavRepository
import com.android.music.ui.Song

class FavViewModel: BaseViewModel() {
    override var dataset: MutableLiveData<ArrayList<Any>> = MutableLiveData()
    var favRepository: FavRepository

    init{
        dataset.value = ArrayList()
        favRepository = FavRepository()
    }
    override fun fillRecyclerView() {
        updateDataset()
    }

    override fun updateDataset() {
        dataset.value = favRepository.getFavSongs() as ArrayList<Any>
    }

    fun getDataset(): ArrayList<Song>{
        return dataset.value as ArrayList<Song>
    }
}