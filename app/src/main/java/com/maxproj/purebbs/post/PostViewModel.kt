package com.maxproj.purebbs.post
import android.app.Application
import android.util.Log
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
        Log.d("PureBBS", "<lifecycle> PostViewModel init")
        val postDao = MyRoomDatabase.getDatabase(application, viewModelScope).postDao()
        postRepository = PostRepository(viewModelScope, postDao, httpApi)
        postList = postRepository.postList
    }

    var postAdapter: PostAdapter = PostAdapter()

    fun changeCategory(category:String){
        postRepository.changeCategory(category)
    }

    fun gotoDetail(view: View, item:Post){
        Navigation.findNavController(view).navigate(PostFragmentDirections.actionPostDestToDetailDest(postId = item.postId))
    }

}

class PostViewModelFactory (private val application: Application, private val httpApi: HttpApi) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(application, httpApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}