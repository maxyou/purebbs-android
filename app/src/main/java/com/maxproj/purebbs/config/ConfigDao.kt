package com.maxproj.purebbs.config

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.maxproj.purebbs.post.Post

@Dao
interface ConfigDao {

    @Query("SELECT * from category_table ORDER BY idStr ASC")
    fun getCategoryList(): LiveData<List<Config.Category>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList( list: List<Config.Category>)
}