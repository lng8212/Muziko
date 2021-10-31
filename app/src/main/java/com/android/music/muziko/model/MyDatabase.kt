package com.android.music.muziko.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.music.ui.Favorites
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Playlist::class, Favorites::class],
    version = 1
)
abstract class MyDatabase : RoomDatabase() {
    abstract fun playlistDAO(): PlaylistDAO
    abstract fun favoriteDao(): FavouriteDao
    private class MyDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populatePlaylistDatabase(database.playlistDAO())
                    populateFavoriteDatabase(database.favoriteDao())
                }
            }
        }

        suspend fun populatePlaylistDatabase(playlistDAO: PlaylistDAO) {
            // Delete all content here.
            playlistDAO.deleteAll()

        }
        suspend fun populateFavoriteDatabase(favoriteDao: FavouriteDao) {
            // Delete all content here.
            favoriteDao.deleteAll()

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