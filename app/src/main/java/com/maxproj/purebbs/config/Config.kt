package com.maxproj.purebbs.config

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URL

object Config {
//    val BASE_URL: URL = URL("http://192.168.31.70:3001")
    val BASE_URL: URL = URL("http://purebbs.com")
    val PATH_AVATAR:String = "user/avatar/"

    @Entity(tableName = "category_table")
    data class Category(@PrimaryKey val idStr: String, val name: String)
    @Entity(tableName = "config_set_table")
    data class ConfigSet(@PrimaryKey val name: String, val value: String)

//    var categories:List<Category>? = null
//    var categoryCurrent:Category? = null

    fun calcAvatarPath(
        source: String,
        avatarFileName: String?,
        oauth_avatarUrl: String?,
        anonymous: Boolean,
        isMyself: Boolean
    ): String {

        fun aa(base:URL, path:String, file:String):String{
            return URL(base, path+file).toString()
        }

        if (anonymous) {
            if (isMyself) {
                return URL(BASE_URL, PATH_AVATAR + "myanonymous.png").toString()
//                return PATH_AVATAR + "myanonymous.png"
            } else {
                return URL(BASE_URL, PATH_AVATAR + "anonymous.png").toString()
//                return PATH_AVATAR + "anonymous.png"
            }
        } else {
            if (source == "oauth") {
                return oauth_avatarUrl!!
            } else {//暂时认为只有 oauth 及 register 两类
                if (avatarFileName != null) {
                    return URL(BASE_URL, PATH_AVATAR + avatarFileName).toString()
//                    return PATH_AVATAR + avatarFileName
                } else {
                    return URL(BASE_URL, PATH_AVATAR + "default.png").toString()
//                    return PATH_AVATAR + "default.png"
                }
            }
        }
    }
}