package com.android.music.muziko.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "playlist_table")
@Parcelize
data class Playlist(@ColumnInfo var name: String = "",
                         @ColumnInfo var countOfSongs: Int = 0,
                         @ColumnInfo var songs: String
): Parcelable {
    @PrimaryKey(autoGenerate = true ) var id: Long = 0
}