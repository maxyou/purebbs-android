package com.maxproj.purebbs.home
import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.net.HttpData
import com.maxproj.purebbs.net.HttpService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HomeViewModel(application: Application, httpApi: HttpApi) : AndroidViewModel(application) {

    private var homeRepository:HomeRepository
    val serverInfo:LiveData<List<ServerInfo>>
    init {
        val homeDao = HomeRoomDatabase.getDatabase(application, viewModelScope).homeDao()
        homeRepository = HomeRepository(viewModelScope, homeDao, httpApi)
        serverInfo = homeRepository.serverInfo
    }

    private val _replyNum = MutableLiveData<Int>(0)
    val replyNum:LiveData<Int> = _replyNum

    fun onClickIncReplyNum(){
        Log.d("PureBBS", "onClickIncReplyNum")
        _replyNum.value = (_replyNum.value ?: 0) + 1
//        _replyNum.setValue(_replyNum.value!! + 1)
        println(_replyNum.value.toString())
    }

    var category:String = "not used"

    var postAdapter: PostAdapter = PostAdapter()

    fun onClickItemUser(view:View){
        Log.d("PureBBS", "onClickItemUser")
    }
    fun gotoDetail(view: View){
        Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeDestToDetailDest())
    }

    fun refresh(){
        homeRepository.serverInfoUpdate()
    }


}