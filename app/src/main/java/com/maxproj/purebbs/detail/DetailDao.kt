package com.maxproj.purebbs.detail

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface DetailDao {

    /**
     * 有如下四种格式，也即有无suspend与有无LiveData的组合
     * 如果生成LiveData，则该函数不会被阻塞，所以没必要suspend
     * 可能编译系统也因为这个原因而禁止同时使用suspend和LiveData
     */
//    @Query("SELECT * from detail_table ORDER BY detailTitle ASC")
//    fun getDetailList(): List<DetailBrief>
//
//    @Query("SELECT * from detail_table ORDER BY detailTitle ASC")
//    suspend fun getDetailList(): List<DetailBrief>
//
    @Query("SELECT * from detail_table")
    fun getDetailList(): LiveData<List<Detail>>

    @Query("SELECT * from detail_table")
    fun getDetailDataSource(): DataSource.Factory<Int, Detail>
//
//    @Query("SELECT * from detail_table ORDER BY detailTitle ASC")
//    suspend fun getDetailList(): LiveData<List<DetailBrief>> //----------- 编译报错


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDetail(detail: Detail)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMany(vararg users: Detail)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList( list: List<Detail>)

    @Delete
    suspend fun deleteDetail(detail: Detail)

    @Query("DELETE FROM detail_table")
    suspend fun deleteAllDetail()

    @Query("SELECT COUNT(_id) FROM detail_table")
    suspend fun getDetailCount(): Int

}