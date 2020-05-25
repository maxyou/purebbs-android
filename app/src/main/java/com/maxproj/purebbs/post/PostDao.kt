package com.maxproj.purebbs.post

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDao {
    @Query("SELECT * from server_info_table ORDER BY info ASC")
    fun getServerInfo(): LiveData<List<ServerInfo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertServerInfo(serverInfo: ServerInfo)

    @Query("DELETE FROM server_info_table")
    suspend fun deleteAllServerInfo()

    @Query("SELECT * from post_table ORDER BY createTime ASC")
    fun getAlphabetizedWords(): LiveData<List<PostBrief>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(postBrief: PostBrief)

    @Query("DELETE FROM post_table")
    suspend fun deleteAll()
}