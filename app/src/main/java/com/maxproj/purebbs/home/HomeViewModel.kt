package com.maxproj.purebbs.home
import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.maxproj.purebbs.net.HttpData
import com.maxproj.purebbs.net.HttpService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var homeRepository:HomeRepository

    private val _replyNum = MutableLiveData<Int>(0)
    val replyNum:LiveData<Int> = _replyNum

    init {
        if(application == null){
            println("application==null in ViewModel")
        }else{
            println("application!=null in ViewModel")
        }
        val homeDao = HomeRoomDatabase.getDatabase(application, viewModelScope).homeDao()
        homeRepository = HomeRepository(homeDao)
    }

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

    }

    fun tryHttp(){

//        var repos = HttpService.api().getPostByPaginate(0, 20)
//        var repos = HttpService.api().getPostByPaginate(0,20 )
//        var repos = HttpService.api().getV2exHot()
        var repos = HttpService.api().getExpressJson()
//        var repos = HttpService.api().getHotTopics(0, "ask", 10, false)

        Log.d("PureBBS", repos.toString())

        repos?.enqueue(object: Callback<HttpData.ExpressJson> {
            override fun onResponse(call: Call<HttpData.ExpressJson>, response: Response<HttpData.ExpressJson>){
                Log.d("PureBBS", "onResponse=======================")
                Log.d("PureBBS", response.body().toString())
                Log.d("PureBBS", response.toString())
                if(response.isSuccessful){

                }
            }

            override fun onFailure(call: Call<HttpData.ExpressJson>, t: Throwable) {
                Log.d("PureBBS", "t.localizedMessage=======================")
                Log.d("PureBBS", t.toString())
                Log.d("PureBBS", t.localizedMessage.toString())
            }
        })

    }

}