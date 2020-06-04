package com.maxproj.purebbs.post
import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.maxproj.purebbs.net.HttpApi

class PostViewModel(application: Application, httpApi: HttpApi) : AndroidViewModel(application) {

    private var postRepository:PostRepository
    val postList:LiveData<List<PostBrief>>

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

    fun refresh(){
//        postRepository.serverInfoUpdate()
//        postRepository.getJsonUserById("abc")
        postRepository.getPostList()
    }


}