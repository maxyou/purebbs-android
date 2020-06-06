package com.maxproj.purebbs.post
import android.app.Application
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.net.HttpData

class PostViewModel(application: Application, httpApi: HttpApi) : AndroidViewModel(application) {

    private var postRepository:PostRepository
    val postList:LiveData<List<Post>>

    init {
        val postDao = PostRoomDatabase.getDatabase(application, viewModelScope).postDao()
        postRepository = PostRepository(viewModelScope, postDao, httpApi)
        postList = postRepository.postList
    }

    var category:String = "not used"

    var postAdapter: PostAdapter = PostAdapter()

    fun gotoDetail(view: View){
        Navigation.findNavController(view).navigate(PostFragmentDirections.actionPostDestToDetailDest())
    }

    fun refreshPostList(){
        val query = HttpData.PostListQuery(
            query = HttpData.PostListQuery.Category("category_dev_web"),
            options = HttpData.PostListQuery.Options(
                offset = 0,
                limit = 10,
                sort = HttpData.PostListQuery.Options.Sort(allUpdated = -1),
                select = "source oauth title postId author authorId commentNum likeUser updated created avatarFileName lastReplyId lastReplyName lastReplyTime allUpdated stickTop category anonymous extend"
            )
        )
        val queryStr = Gson().toJson(query)
        postRepository.refreshPostList(queryStr)
    }


}