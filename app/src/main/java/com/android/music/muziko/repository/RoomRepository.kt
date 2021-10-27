package com.android.music.muziko.repository

import com.android.music.muziko.appInterface.RoomRepositoryInterface
import com.android.music.muziko.model.MyDatabase
import com.android.music.muziko.model.Playlist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

object RoomRepository : RoomRepositoryInterface{

    private val applicationScope = CoroutineScope(SupervisorJob())
    lateinit var localDatabase: MyDatabase
    var cachedPlaylistArray = ArrayList<Playlist>()
    override fun createDatabase() {
        TODO("Not yet implemented")
    }

    override fun createPlaylist(playlist: Playlist) {
        applicationScope.launch {
            localDatabase.playlistDAO().addPlaylist(playlist)
        }
        cachedPlaylistArray.add(playlist)
    }

    override fun removePlaylist(id: Long): Boolean {
        applicationScope.launch {
            localDatabase.playlistDAO().deletePlaylist(id)

            cachedPlaylistArray =
                localDatabase.playlistDAO().getPlaylists() as ArrayList<Playlist>
        }

        return true
    }

    override fun getPlaylists(): ArrayList<Playlist> {
        TODO("Not yet implemented")
    }

    override fun addSongsToPlaylist(playlist_name: String, songsId: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeSongFromPlaylist(playlistId: Long, songsId: String) {
        val playlist = getPlaylistById(playlistId)


        if (playlist != null) {

            val position = findPlaylistPositionInCachedArray(playlist)

            if (position >= 0) {
                //remove song from playlist object
                removeSongFromPlaylistObject(cachedPlaylistArray[position], songsId)

                //decrease count of song in playlist object
                cachedPlaylistArray[position].countOfSongs--

            }

            GlobalScope.launch {
                //update songs in database
                localDatabase.playlistDAO().updateSongs(playlistId, playlist.songs)

                //update count of songs in database
                decreaseCountInDatabase(playlistId, playlist.countOfSongs)
            }

        }
    }

    override fun listOfPlaylistsContainSpecificSong(songId: Long): ArrayList<Long> {
        TODO("Not yet implemented")
    }

    override fun removeSongFromPlaylistObject(playlist: Playlist, songsId: String) {
        TODO("Not yet implemented")
    }

    override fun decreaseCountInDatabase(playlistId: Long, countOfSongs: Int) {
        TODO("Not yet implemented")
    }

    override fun increaseCountInPlaylistObject(playlist: Playlist) {
        TODO("Not yet implemented")
    }

    override fun increaseCountInDatabase(playlist: Playlist) {
        TODO("Not yet implemented")
    }

    override fun addSongsToPlaylistInObject(playlist: Playlist, songsId: String) {
        TODO("Not yet implemented")
    }

    override fun addSongsToPlaylistInDatabase(playlist: Playlist, songsId: String) {
        TODO("Not yet implemented")
    }

    override fun getPlaylistFromDatabase(): ArrayList<Playlist> {
        TODO("Not yet implemented")
    }

    override fun updateCachedPlaylist() {
        TODO("Not yet implemented")
    }

    override fun findPlaylistPositionInCachedArray(playlist: Playlist): Int {
        TODO("Not yet implemented")
    }

    override fun getIdByName(name: String): Long {
        TODO("Not yet implemented")
    }

    override fun getPlaylistById(id: Long): Playlist? {
        TODO("Not yet implemented")
    }
}