package com.maxproj.purebbs.config

import java.net.URL

object Config {
    val BASE_URL: URL = URL("http://192.168.31.70:3001")
    val PATH_AVATAR:String = "user/avatar/"

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