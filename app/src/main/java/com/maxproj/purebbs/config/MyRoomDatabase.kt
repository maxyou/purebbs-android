package com.maxproj.purebbs.config

import android.content.Context
import android.util.Log
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.maxproj.purebbs.post.Post
import com.maxproj.purebbs.post.PostDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class Converters {
    private val SEPARATOR = " " //这里不能使用Gson().toJson(item)返回的字符串里可能的字符，比如逗号或冒号

    @TypeConverter
    fun strToListLikeUser(str: String?): List<Post.LikeUser?>? {
        Log.d("PureBBS", "TypeConverter str2ListLikeUser 1:$str")
        val list = str?.split(SEPARATOR)?.map {
            Post.LikeUser.fromJsonStr(
                it
            )
        }
        Log.d("PureBBS", "TypeConverter str2ListLikeUser 2:$list")
        return list
    }
    @TypeConverter
    fun listLikeUserToStr(listLikeUser: List<Post.LikeUser>): String? {
        Log.d("PureBBS", "TypeConverter listLikeUser2Str 1:${listLikeUser}")
        val str = listLikeUser.map { it.toJsonStr() }.joinToString (separator = SEPARATOR)
        Log.d("PureBBS", "TypeConverter listLikeUser2Str 2:$str")
        return str
    }
    @TypeConverter
    fun toOauth(str: String?): Post.Oauth? {
        return Gson().fromJson(str, Post.Oauth::class.java)
    }
    @TypeConverter
    fun fromOauth(oauth: Post.Oauth?): String? {
        return Gson().toJson(oauth)
    }
    @TypeConverter
    fun toExtend(str: String?): Post.Extend? {
        return Gson().fromJson(str, Post.Extend::class.java)
    }
    @TypeConverter
    fun fromExtend(extend: Post.Extend?): String? {
        return Gson().toJson(extend)
    }
}

@Database(entities = arrayOf(Post::class, Config.Category::class), version = 17, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MyRoomDatabase : RoomDatabase(){

    abstract fun postDao(): PostDao
    abstract fun configDao(): ConfigDao //include config and category

    companion object {
        private val DATABASE_NAME = "purebbs_database2"
        @Volatile
        private var INSTANCE: MyRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): MyRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE
                ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyRoomDatabase::class.java,
                    DATABASE_NAME
                )
                    .addCallback(DatabaseCallback(scope))
                    .fallbackToDestructiveMigration() //this will remove all data of last version, just for dev
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class DatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {

                    /**
                     * 哪些表需要初始化？
                     * config表？
                     */
//                    var postDao = database.postDao()
//                    postDao.deleteAllPost()// Delete all content here.
                    // Add sample words.
//                    var postBrief = PostBrief(
//                        "0",
//                        "Alex",
//                        "",
//                        "I find something!",
//                        "share",
//                        "2020-0519-0509-01-001",
//                        1,
//                        false
//                    )
//                    postDao.insertPost(postBrief)

                }
            }
        }
    }
}