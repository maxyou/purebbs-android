package com.maxproj.purebbs.post
import android.app.Application
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.Navigation
import androidx.paging.PagedList
import com.google.gson.Gson
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.net.HttpData

class PostViewModel(application: Application, httpApi: HttpApi) : AndroidViewModel(application) {

    private var postRepository:PostRepository
    val postList:LiveData<PagedList<Post>>?

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

}