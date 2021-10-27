package com.android.music.muziko.appInterface

import com.android.music.ui.Song

interface PlaylistPageRepositoryInterface {
    fun getSongsIdFromDatabase(): String
    fun songsIdToSongModelConverter(songId: String): Song?
    fun getSongs(): ArrayList<Song>
}