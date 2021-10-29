package com.android.music.muziko.repository

import android.content.Context
import com.android.music.muziko.appInterface.PlaylistRepositoryInterface
import com.android.music.muziko.model.Playlist

class PlaylistRepository (val context: Context?) :
    PlaylistRepositoryInterface{
    override fun createPlaylist(name: String) {
        val playlist = Playlist(name, 0, "")
        RoomRepository.createPlaylist(playlist)
    }

    override fun createPlaylist(name: String, countOfSongs: Int, songs: String) {

        val playlist = Playlist(name, countOfSongs, songs)
        RoomRepository.createPlaylist(playlist)
    }

    override fun getPlaylists(): ArrayList<Playlist> {
        return RoomRepository.cachedPlaylistArray
    }

    override fun removePlaylist(id: Long): Boolean {
        RoomRepository.removePlaylist(id)
        return true
    }

    fun removeSongFromPlaylist(playlistId: Long, songsId: String) {

        RoomRepository.removeSongFromPlaylist(playlistId, songsId)
    }
}