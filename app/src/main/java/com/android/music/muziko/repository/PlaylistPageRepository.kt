package com.android.music.muziko.repository

import com.android.music.muziko.appInterface.PlaylistPageRepositoryInterface
import com.android.music.muziko.utils.DatabaseConverterUtils
import com.android.music.ui.Song
import kotlinx.coroutines.runBlocking

class PlaylistPageRepository(private val playlistId: Long):
    PlaylistPageRepositoryInterface{
    override fun getSongsIdFromDatabase(): String {
        var songsOfPlaylist: String = ""
        runBlocking {
            songsOfPlaylist = RoomRepository.localDatabase.playlistDAO().getSongsOfPlaylist(playlistId)
        }
        return songsOfPlaylist
    }

    override fun songsIdToSongModelConverter(songId: String): Song? {
        TODO("Not yet implemented")
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