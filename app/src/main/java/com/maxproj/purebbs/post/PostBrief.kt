package com.maxproj.purebbs.post

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

@Entity(tableName = "server_info_table")
data class ServerInfo (
    @PrimaryKey var _id:Int,
    var info:String
)