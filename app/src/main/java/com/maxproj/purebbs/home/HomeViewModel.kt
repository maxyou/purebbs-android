package com.maxproj.purebbs.home
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private var _replyNum = MutableLiveData<Int?>(0)
    val replyNum:LiveData<Int?>
        get() = _replyNum

    fun onClickIncReplyNum(){
        Log.d("PureBBS", "onClickIncReplyNum")
        _replyNum.value = _replyNum.value?.plus(1)
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
}