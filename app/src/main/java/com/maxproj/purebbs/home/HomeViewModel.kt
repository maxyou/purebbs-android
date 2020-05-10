package com.maxproj.purebbs.home
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation

class HomeViewModel : ViewModel() {

    fun gotoDetail(view: View){

        Navigation.findNavController(view).navigate(HomeFragmentDirections.actionHomeDestToDetailDest())

    }
}