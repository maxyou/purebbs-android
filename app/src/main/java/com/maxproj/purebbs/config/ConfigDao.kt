package com.maxproj.purebbs.config

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*

@Dao
interface ConfigDao {

    @Query("SELECT * from config_table ORDER BY idStr ASC")
    suspend fun getCategoryList(): List<Config.Category>

}