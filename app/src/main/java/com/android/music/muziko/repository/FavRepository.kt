package com.android.music.muziko.repository

import com.android.music.ui.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class FavRepository {
    val applicationScope = CoroutineScope(SupervisorJob())
    companion object {
        var cashedFavArray = RoomRepository.cachedFavArray
    }

    init {
        applicationScope.launch {
            cashedFavArray =
                RoomRepository.cachedFavArray
        }
    }

    fun getFavSongs(): ArrayList<Song> {

        return RoomRepository.cachedFavArray

    }
}