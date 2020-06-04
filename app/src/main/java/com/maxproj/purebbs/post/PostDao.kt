package com.maxproj.purebbs.post

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PostDao {
    //-----------PostBrief--------------------
    @Query("SELECT * from post_table ORDER BY postTitle ASC")
    fun getPostList(): List<PostBrief>
    @Query("SELECT * from post_table ORDER BY postTitle ASC")
    suspend fun getPostList2(): List<PostBrief>
    @Query("SELECT * from post_table ORDER BY postTitle ASC")
    fun getPostList3(): LiveData<List<PostBrief>>
//    @Query("SELECT * from post_table ORDER BY postTitle ASC")
//    suspend fun getPostList4(): LiveData<List<PostBrief>>


//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertPost(postBrief: PostBrief)
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertMany(vararg users: PostBrief)
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertList( list: List<PostBrief>)
//
//    @Delete
//    suspend fun deletePost(postBrief: PostBrief)

//    @Query("DELETE FROM post_table")
//    suspend fun deleteAllPost()

    //-------------for test----------------
    @Query("SELECT * from server_info_table ORDER BY info ASC")
    fun getServerInfo(): LiveData<List<ServerInfo>>

    @Query("SELECT * from server_info_table ORDER BY info ASC")
    fun getServerInfo2(): List<ServerInfo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertServerInfo(serverInfo: ServerInfo)

    @Query("DELETE FROM server_info_table")
    suspend fun deleteAllServerInfo()

    @Delete
    suspend fun delete(serverInfo: ServerInfo)}