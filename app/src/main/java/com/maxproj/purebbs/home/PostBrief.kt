package com.maxproj.purebbs.home

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class PostBrief (
    @PrimaryKey var _id:Int,
    var user:String,
    var postTitle:String,
    var postCategory:String,
    var createTime:String
)