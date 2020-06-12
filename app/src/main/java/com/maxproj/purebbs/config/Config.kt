package com.maxproj.purebbs.config

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maxproj.purebbs.post.PostViewModel
import java.net.URL
import java.util.*
import kotlin.properties.Delegates

object Config {
//    val BASE_URL: URL = URL("http://192.168.31.70:3001")
    val BASE_URL: URL = URL("http://purebbs.com")
    val PATH_AVATAR:String = "user/avatar/"
    val CATEGORY_ALL:String = "category_all"
//    var categoryCurrent:String = Config.CATEGORY_ALL

    val _categoryCurrentLive: MutableLiveData<String> by lazy {
        MutableLiveData<String>(Config.CATEGORY_ALL)
    }
    val categoryCurrentLive: LiveData<String>
        get() = _categoryCurrentLive

//    var categories:List<Category>? = null
//    var categoryCurrent:Category? = null

    val DATABASE_NAME:String = "purebbs_database11"

    fun calcAvatarPath(
        source: String?,
        avatarFileName: String?,
        oauth_avatarUrl: String?,
        anonymous: Boolean,
        isMyself: Boolean
    ): String {

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
            } else {//暂时认为只有 oauth 及 register 两类，null缺省是register
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