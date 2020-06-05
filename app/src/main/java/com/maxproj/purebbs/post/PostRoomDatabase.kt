package com.maxproj.purebbs.post

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(PostBrief::class), version = 4, exportSchema = false)
abstract class PostRoomDatabase : RoomDatabase(){

    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private var INSTANCE: PostRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): PostRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostRoomDatabase::class.java,
                    "post_database"
                )
                    .addCallback(PostDatabaseCallback(scope))
                    .fallbackToDestructiveMigration() //this will remove all data of last version, just for dev
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class PostDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var postDao = database.postDao()

                    postDao.deleteAllPost()// Delete all content here.
                    // Add sample words.
                    var postBrief = PostBrief(
                        "0",
                        "Alex",
                        "I find something!",
                        "share",
                        "2020-0519-0509-01-001",
                        1,
                        false
                    )
                    postDao.insertPost(postBrief)

                }
            }
        }
    }

}