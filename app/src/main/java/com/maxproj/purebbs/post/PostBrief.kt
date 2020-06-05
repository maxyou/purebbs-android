package com.maxproj.purebbs.post

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post_table")
data class PostBrief (
    @PrimaryKey var _id:String,
    var user:String,
    var avatarFileName:String?,
    var postTitle:String?,
    var postCategory:String?,
    var createTime:String?,
    var commentNum:Int?,
    var stickTop:Boolean?
)
