package com.android.music.ui

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class Favorites(
    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo var fId : Long,
    @ColumnInfo val songId: Long
)