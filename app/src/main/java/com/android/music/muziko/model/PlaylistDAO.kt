package com.android.music.muziko.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlaylistDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlaylist(playlist: Playlist)


    @Query("DELETE FROM playlist_table WHERE id = :playlistId")
    suspend fun deletePlaylist(playlistId: Long)


    @Query("DELETE FROM playlist_table")
    suspend fun deleteAll()


    @Query("SELECT * FROM playlist_table")
    fun getPlaylists(): List<Playlist>


    @Query("UPDATE playlist_table SET songs=:songs WHERE id = :id")
    suspend fun addSongToPlaylist(id: Long, songs: String)

    @Query("SELECT songs FROM playlist_table WHERE id = :id")
    suspend fun getSongsOfPlaylist(id: Long): String

    @Query("SELECT countOfSongs FROM playlist_table WHERE id = :id")
    fun getCountOfSongsInPlaylist(id: Long): Int

    @Query("UPDATE playlist_table SET countOfSongs = :count WHERE id = :id")
    fun setCountOfSongs(id: Long, count: Int)

    @Query("UPDATE playlist_table SET songs = :songs WHERE id = :id")
    fun updateSongs(id: Long, songs: String)

}