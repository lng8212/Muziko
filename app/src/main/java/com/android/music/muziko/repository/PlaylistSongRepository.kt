package com.android.music.muziko.repository

import com.android.music.muziko.appInterface.PlaylistPageRepositoryInterface
import com.android.music.muziko.utils.DatabaseConverterUtils
import com.android.music.ui.Song
import com.android.music.ui.fragments.SongFragment
import kotlinx.coroutines.runBlocking

class PlaylistSongRepository(private val playlistId: String, val array: ArrayList<Song>):
    PlaylistPageRepositoryInterface{
    override fun getSongsIdFromDatabase(): String {
        var songsOfPlaylist: String = ""
        runBlocking {
            songsOfPlaylist = RoomRepository.localDatabase.playlistDAO().getSongsOfPlaylist(playlistId)
        }
        return songsOfPlaylist
    }

    override fun songsIdToSongModelConverter(songId: String): Song? {

        for (song in array) {
            if (song.id == songId.toLong()) {
                return song
            }
        }
        return null
    }

    override fun getSongs(): ArrayList<Song> {
        var songs: ArrayList<Song> = arrayListOf()

        val songsIdInString = getSongsIdFromDatabase()
        if (songsIdInString != null) {
            val songsIdInArraylist = convertStringToArraylist(songsIdInString)

            for (songId in songsIdInArraylist) {
                val realSong = songsIdToSongModelConverter(songId)
                if (realSong != null)
                    songs.add(realSong)
            }
        }

        return songs
    }

    fun convertStringToArraylist(songs: String): ArrayList<String> {
        return DatabaseConverterUtils.stringToArraylist(songs)
    }


}