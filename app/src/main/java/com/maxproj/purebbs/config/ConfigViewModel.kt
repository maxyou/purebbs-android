package com.maxproj.purebbs.config

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import androidx.navigation.Navigation
import androidx.paging.PagedList
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.post.*

class ConfigViewModel (application: Application, httpApi: HttpApi) : AndroidViewModel(application) {

    private var configRepository: ConfigRepository
    val categoryList: LiveData<List<Config.Category>>?

    init {
        val postDao = PostRoomDatabase.getDatabase(application, viewModelScope).postDao()
        configRepository = ConfigRepository(viewModelScope, postDao, httpApi)
        categoryList = configRepository.categoryList
    }

    val adapter = CategoryAdapter()

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