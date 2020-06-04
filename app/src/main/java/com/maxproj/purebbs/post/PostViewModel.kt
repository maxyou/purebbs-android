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
    val serverInfo:LiveData<List<ServerInfo>>
    init {
        val postDao = PostRoomDatabase.getDatabase(application, viewModelScope).postDao()
        postRepository = PostRepository(viewModelScope, postDao, httpApi)
        serverInfo = postRepository.serverInfo
        postList = postRepository.postList
    }

    private val _replyNum = MutableLiveData<Int>(0)
    val replyNum:LiveData<Int> = _replyNum

    fun onClickIncReplyNum(){
        Log.d("PureBBS", "onClickIncReplyNum")
        _replyNum.value = (_replyNum.value ?: 0) + 1
//        _replyNum.setValue(_replyNum.value!! + 1)
        println(_replyNum.value.toString())

        postAdapter.data = mockPostData()
    }

    var category:String = "not used"

    var postAdapter: PostAdapter = PostAdapter()

    fun onClickItemUser(view:View){
        Log.d("PureBBS", "onClickItemUser")
    }
    fun gotoDetail(view: View){
        Navigation.findNavController(view).navigate(PostFragmentDirections.actionPostDestToDetailDest())
    }

    fun refresh(){
//        postRepository.serverInfoUpdate()
//        postRepository.getJsonUserById("abc")
        postRepository.getPostList()
    }


}