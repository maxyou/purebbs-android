package com.maxproj.purebbs.home

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.maxproj.purebbs.home.PostBrief
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(PostBrief::class), version = 1, exportSchema = false)
abstract class HomeRoomDatabase : RoomDatabase(){

    abstract fun homeDao(): HomeDao

    companion object {
        @Volatile
        private var INSTANCE: HomeRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): HomeRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HomeRoomDatabase::class.java,
                    "home_database"
                )
                    .addCallback(HomeDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class HomeDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var homeDao = database.homeDao()
                    homeDao.deleteAll()// Delete all content here.
                    // Add sample words.
                    var postBrief = PostBrief(
                        0,
                        "Alex",
                        "I find something!",
                        "share",
                        "2020-0519-0509-01-001"
                    )
                    homeDao.insert(postBrief)
                }
            }
        }
    }

}