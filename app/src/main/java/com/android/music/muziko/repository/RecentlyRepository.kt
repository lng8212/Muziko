package com.android.music.muziko.repository

import android.util.Log
import androidx.room.Room
import com.android.music.muziko.model.Recently
import com.android.music.muziko.model.Song
import com.android.music.ui.fragments.LibraryFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RecentlyRepository {
    val applicationScope = CoroutineScope(SupervisorJob())
    companion object{
        var cashedRecArray = RoomRepository.cachedRecArray
    }

    init{
        applicationScope.launch {
            cashedRecArray = RoomRepository.cachedRecArray
        }
    }

    fun getRecSongs(): ArrayList<Song>{
        RoomRepository.updateCachedRecently()
        RoomRepository.convertRecentlySongsToRealSongs()
        return RoomRepository.cachedRecArray
    }

    fun addSong(songId: Long){
        Log.e("RecentlyRepository", songId.toString())
        RoomRepository.addSongToRecently(songId)
    }

    fun removeSong(song: Song){
        RoomRepository.removeSongFromRecently(song)
    }
}