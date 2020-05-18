package com.maxproj.purebbs.home

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HomeDao {
    @Query("SELECT * from post_table ORDER BY createTime ASC")
    fun getAlphabetizedWords(): LiveData<List<PostBrief>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(postBrief: PostBrief)

    @Query("DELETE FROM post_table")
    suspend fun deleteAll()
}