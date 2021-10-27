package com.android.music.muziko.appInterface

import com.android.music.muziko.model.Playlist

interface RoomRepositoryInterface {

    // ----------------------- init Database ------------------------
    fun createDatabase()

    // ----------------------- playlist ------------------------
    fun createPlaylist(playlist: Playlist)
    fun removePlaylist(id: Long): Boolean
    fun getPlaylists(): ArrayList<Playlist>
    fun addSongsToPlaylist(playlist_name: String, songsId: String): Boolean
    fun removeSongFromPlaylist(playlistId: Long, songsId: String)
    fun listOfPlaylistsContainSpecificSong(songId: Long): ArrayList<Long>
    fun removeSongFromPlaylistObject(playlist: Playlist, songsId: String) //check
    fun decreaseCountInDatabase(playlistId: Long, countOfSongs: Int)
    fun increaseCountInPlaylistObject(playlist: Playlist)
    fun increaseCountInDatabase(playlist: Playlist)
    fun addSongsToPlaylistInObject(playlist: Playlist, songsId: String) //check
    fun addSongsToPlaylistInDatabase(playlist: Playlist, songsId: String)
    fun getPlaylistFromDatabase(): ArrayList<Playlist>

    fun updateCachedPlaylist()
    fun findPlaylistPositionInCachedArray(playlist: Playlist): Int
    fun getIdByName(name: String): Long
    fun getPlaylistById(id: Long): Playlist?
}