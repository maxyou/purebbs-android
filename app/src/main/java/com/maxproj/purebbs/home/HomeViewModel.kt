package com.maxproj.purebbs.home
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    var itemFlag:String = "not used"

    var postAdapter: PostAdapter = PostAdapter()

    fun onClickItemUser(view:View){
        Log.d("PureBBS", "onClickItemUser")
    }
    fun gotoDetail(view: View){

        Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeDestToDetailDest())

    }
}