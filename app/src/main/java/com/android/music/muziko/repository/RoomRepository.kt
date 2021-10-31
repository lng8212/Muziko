package com.android.music.muziko.repository

import androidx.lifecycle.lifecycleScope
import com.android.music.muziko.appInterface.RoomRepositoryInterface
import com.android.music.muziko.model.MyDatabase
import com.android.music.muziko.model.Playlist
import com.android.music.muziko.utils.DatabaseConverterUtils
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


    //    ----------------------------------------------- Database ----------------------------------------------------
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

    //    ----------------------------------------------- Playlist ----------------------------------------------------
    override fun updateCachedPlaylist() {
        GlobalScope.launch {
            cachedPlaylistArray = getPlaylistFromDatabase()
        }
    }

    override fun createPlaylist(playlist: Playlist) {
        applicationScope.launch {
            localDatabase.playlistDAO().addPlaylist(playlist)
        }
        cachedPlaylistArray.add(playlist)
    }

    override fun removePlaylist(id: String): Boolean {
        applicationScope.launch {
            localDatabase.playlistDAO().deletePlaylist(id)

            cachedPlaylistArray =
                localDatabase.playlistDAO().getPlaylists() as ArrayList<Playlist>
        }

        return true
    }

    override fun getPlaylists(): ArrayList<Playlist> {
        return cachedPlaylistArray
    }

    override fun addSongsToPlaylist(playlist_name: String, songsId: String): Boolean {

        val playlist = getPlaylistById(getIdByName(playlist_name))

//        add song to playlist object
        if (playlist != null) {

            val position = findPlaylistPositionInCachedArray(playlist)

            if (position >= 0) {
                addSongsToPlaylistInObject(
                    cachedPlaylistArray[position],
                    songsId
                )
            }

            addSongsToPlaylistInDatabase(playlist, songsId)

        }

        return true
    }

    override fun removeSongFromPlaylist(playlistId: String, songsId: String) {
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

    override fun listOfPlaylistsContainSpecificSong(songId: Long): ArrayList<String>
    {
        var pls = arrayListOf<String>()

        for(playlist in cachedPlaylistArray)
        {
            val ids = DatabaseConverterUtils.stringToArraylist(playlist.songs)
            for(id in ids)
            {
                if (id.toLong() == songId)
                {
                    pls.add(playlist.id)
                }
            }
        }

        return pls
    }

    override fun removeSongFromPlaylistObject(playlist: Playlist, songsId: String) {
        val songsInAray = DatabaseConverterUtils.stringToArraylist(playlist.songs)
        songsInAray.remove(songsId)
        val songsInString = DatabaseConverterUtils.arraylistToString(songsInAray)
        playlist.songs = songsInString
    }

    override fun decreaseCountInDatabase(playlistId: String, countOfSongs: Int) {

        GlobalScope.launch {
            localDatabase.playlistDAO()
                .setCountOfSongs(playlistId, countOfSongs)

        }
    }

    override fun increaseCountInPlaylistObject(playlist: Playlist) {
        playlist.countOfSongs = playlist.countOfSongs + 1
    }

    override fun increaseCountInDatabase(playlist: Playlist) {
        GlobalScope.launch {
            localDatabase.playlistDAO()
                .setCountOfSongs(playlist.id, playlist.countOfSongs)
        }
    }

    override fun addSongsToPlaylistInObject(playlist: Playlist, songsId: String) {

        val position = findPlaylistPositionInCachedArray(playlist)

        cachedPlaylistArray[position].songs =
            cachedPlaylistArray[position].songs + songsId + ","

        increaseCountInPlaylistObject(cachedPlaylistArray[position])
    }

    override fun addSongsToPlaylistInDatabase(playlist: Playlist, songsId: String) {

        runBlocking {

            if (playlist != null) {
                for (song_id in songsId) {
                    localDatabase.playlistDAO().addSongToPlaylist(
                        playlist.id,
                        playlist.songs
                    )
                }
                increaseCountInDatabase(playlist)
            }
//            TODO(Toast : operation failed! please try later)
        }
    }

    override fun getPlaylistFromDatabase(): ArrayList<Playlist> =
        runBlocking {
            val playlistsList = localDatabase.playlistDAO().getPlaylists()
            val arrayList = arrayListOf<Playlist>()
            for (playlist in playlistsList) {
                arrayList.add(playlist)
            }
            return@runBlocking arrayList
        }


    override fun findPlaylistPositionInCachedArray(playlist: Playlist): Int {
        TODO("Not yet implemented")
    }

    override fun getIdByName(name: String): String {
        TODO("Not yet implemented")
    }

    override fun getPlaylistById(id: String): Playlist? {
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