package com.maxproj.purebbs.config

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.post.*

class ConfigViewModel (application: Application, httpApi: HttpApi) : AndroidViewModel(application) {

    private var configRepository: ConfigRepository
    val categoryList: LiveData<List<Config.Category>>?
    val configDao:ConfigDao = MyRoomDatabase.getDatabase(application, viewModelScope).configDao()
    val postDao:PostDao = MyRoomDatabase.getDatabase(application, viewModelScope).postDao()

    init {
        configRepository = ConfigRepository(viewModelScope, configDao, httpApi)
        categoryList = configRepository.categoryList
    }

    val categoryAdapter = CategoryAdapter()

    fun updateCategoryList(){
        configRepository.updateCategoryList()
    }
    fun updateCategoryCurrent(view: View, idStr:String){
        Log.d("PureBBS", "new category current: $idStr")
//        categoryCurrent = idStr
//        viewModelScope.launch {
//            Log.d("PureBBS", "postDao.deleteAllPost()")
//            postDao.deleteAllPost()
//        }
        Config._categoryCurrentLive.value = idStr
    }
    fun gotoDetail(view: View){
        Navigation.findNavController(view).navigate(PostFragmentDirections.actionPostDestToDetailDest())
    }

}

class ConfigViewModelFactory (private val application: Application, private val httpApi: HttpApi) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfigViewModel::class.java)) {
            return ConfigViewModel(application, httpApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}