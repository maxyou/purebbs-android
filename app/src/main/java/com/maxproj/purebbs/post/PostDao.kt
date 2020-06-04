package com.maxproj.purebbs.post

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PostDao {

    /**
     * 有如下四种格式，也即有无suspend与有无LiveData的组合
     * 如果生成LiveData，则该函数不会被阻塞，所以没必要suspend
     * 可能编译系统也因为这个原因而禁止同时使用suspend和LiveData
     */
//    @Query("SELECT * from post_table ORDER BY postTitle ASC")
//    fun getPostList(): List<PostBrief>
//
//    @Query("SELECT * from post_table ORDER BY postTitle ASC")
//    suspend fun getPostList(): List<PostBrief>
//
    @Query("SELECT * from post_table ORDER BY postTitle ASC")
    fun getPostList(): LiveData<List<PostBrief>>
//
//    @Query("SELECT * from post_table ORDER BY postTitle ASC")
//    suspend fun getPostList(): LiveData<List<PostBrief>> //----------- 编译报错

    //-----------PostBrief--------------------

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPost(postBrief: PostBrief)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMany(vararg users: PostBrief)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList( list: List<PostBrief>)

    @Delete
    suspend fun deletePost(postBrief: PostBrief)

    @Query("DELETE FROM post_table")
    suspend fun deleteAllPost()

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