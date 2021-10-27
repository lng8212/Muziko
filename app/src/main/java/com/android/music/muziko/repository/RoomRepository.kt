package com.android.music.muziko.repository

import androidx.lifecycle.lifecycleScope
import com.android.music.muziko.appInterface.RoomRepositoryInterface
import com.android.music.muziko.model.MyDatabase
import com.android.music.muziko.model.Playlist
import com.android.music.muziko.utils.SongUtils
import com.android.music.ui.Favorites
import com.android.music.ui.Song
import com.android.music.ui.activity.MainActivity
import com.android.music.ui.fragments.LibraryFragment
import kotlinx.coroutines.*

object RoomRepository : RoomRepositoryInterface{

    private val applicationScope = CoroutineScope(SupervisorJob())
    lateinit var localDatabase: MyDatabase
    var cachedPlaylistArray = ArrayList<Playlist>()
    var cachedFavArray_Favorites = ArrayList<Favorites>()
    var cachedFavArray = ArrayList<Song>()
    override fun createDatabase() {
        localDatabase = MyDatabase.getDatabase(
            MainActivity.activity.baseContext!!,
            MainActivity.activity.lifecycleScope
        )
        applicationScope.launch {
            cachedPlaylistArray = getPlaylistFromDatabase()
            cachedFavArray_Favorites = getFavoritesFromDatabase()
        }
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

    override fun addSongToFavorites(songsId: Long) {
        val fav = Favorites(songsId)
        applicationScope.launch {
            localDatabase.favoriteDao().addSong(fav)
        }

        SongUtils.getSongById(songsId)?.let { cachedFavArray.add(it) }
    }

    fun removeSongFromDB(song: Song)
    {

        val fav = Favorites(song.id!!)
        applicationScope.launch {
            localDatabase.favoriteDao().deleteSong(fav)
        }

    }
    private fun removeSongFromCachedFavArray(song: Song)
    {
        val iter: MutableIterator<Song> = cachedFavArray.iterator()

        while (iter.hasNext()) {
            if (iter.next().id!! == song!!.id) iter.remove()
        }
    }
    override fun removeSongFromFavorites(song: Song) {
        removeSongFromDB(song)
        removeSongFromCachedFavArray(song)
    }

    override fun getFavoritesFromDatabase(): ArrayList<Favorites> =
        runBlocking {
            val favSongs = localDatabase.favoriteDao().getFavs()

            val arr = arrayListOf<Favorites>()
            arr.addAll(favSongs)
            return@runBlocking arr
        }

    override fun convertFavSongsToRealSongs(): ArrayList<Song> {
        val arrayList = arrayListOf<Song>()
        for (favSong in cachedFavArray_Favorites) {

            val realSong = songsIdToSongModelConverter(favSong)
            if (realSong != null)
                arrayList.add(realSong)
        }

        cachedFavArray = arrayList
        return arrayList
    }

    override fun songsIdToSongModelConverter(favSong: Favorites): Song? {
        val allSongsInStorage = LibraryFragment.viewModel.getData()

        for (song in allSongsInStorage) {
            if (song.id == favSong.songId) {
                return song
            }
        }
        return null
    }
}