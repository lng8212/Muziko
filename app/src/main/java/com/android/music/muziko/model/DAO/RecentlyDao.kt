package com.android.music.muziko.model.DAO

import androidx.room.*
import com.android.music.muziko.model.Recently

@Dao
interface RecentlyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSong(song: Recently)

    @Delete
    suspend fun deleteSong(song: Recently)

    @Query("DELETE FROM recently_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM recently_table")
    fun getRecently(): List<Recently>
}