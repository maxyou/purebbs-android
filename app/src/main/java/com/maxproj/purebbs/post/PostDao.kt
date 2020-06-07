package com.maxproj.purebbs.post

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
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
    @Query("SELECT * from post_table ORDER BY title ASC")
    fun getPostList(): LiveData<List<Post>>

    @Query("SELECT * from post_table ORDER BY title ASC")
    fun getPostDataSource(): DataSource.Factory<Int, Post>
//
//    @Query("SELECT * from post_table ORDER BY postTitle ASC")
//    suspend fun getPostList(): LiveData<List<PostBrief>> //----------- 编译报错


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPost(post: Post)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMany(vararg users: Post)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList( list: List<Post>)

    @Delete
    suspend fun deletePost(post: Post)

    @Query("DELETE FROM post_table")
    suspend fun deleteAllPost()

    @Query("SELECT COUNT(_id) FROM post_table")
    suspend fun getPostCount(): Int

}