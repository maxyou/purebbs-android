package com.maxproj.purebbs.post
import android.app.Application
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.Navigation
import androidx.paging.PagedList
import com.maxproj.purebbs.config.MyRoomDatabase
import com.maxproj.purebbs.net.HttpApi

class PostViewModel(application: Application, httpApi: HttpApi) : AndroidViewModel(application) {

    private var postRepository:PostRepository
    val postList:LiveData<PagedList<Post>>?

    init {
        val postDao = MyRoomDatabase.getDatabase(application, viewModelScope).postDao()
        postRepository = PostRepository(viewModelScope, postDao, httpApi)
        postList = postRepository.postList
    }

    var postAdapter: PostAdapter = PostAdapter()

    fun gotoDetail(view: View){
        Navigation.findNavController(view).navigate(PostFragmentDirections.actionPostDestToDetailDest())
    }

}