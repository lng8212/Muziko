package com.android.music.muziko.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Playlist::class],
    version = 1
)
abstract class MyDatabase : RoomDatabase() {
    abstract fun playlistDAO(): PlaylistDAO

    private class MyDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populatePlaylistDatabase(database.playlistDAO())
                }
            }
        }

        suspend fun populatePlaylistDatabase(playlistDAO: PlaylistDAO) {
            // Delete all content here.
            playlistDAO.deleteAll()

        }
    }

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MyDatabase {
            return INSTANCE ?: synchronized(MyDatabase::class.java)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyDatabase::class.java, "_database"
                )
                    .addCallback(MyDatabaseCallback(scope))
                    .build()
                INSTANCE = instance

//                return instance
                instance
            }
        }


    }
}