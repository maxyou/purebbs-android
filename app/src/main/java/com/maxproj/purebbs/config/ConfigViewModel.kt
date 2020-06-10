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
    var categoryCurrent:String = Config.CATEGORY_ALL

    init {
        val configDao = MyRoomDatabase.getDatabase(application, viewModelScope).configDao()
        configRepository = ConfigRepository(viewModelScope, configDao, httpApi)
        categoryList = configRepository.categoryList
    }

    val categoryAdapter = CategoryAdapter()

    fun updateCategoryList(){
        configRepository.updateCategoryList()
    }
    fun updateCategoryCurrent(view: View, idStr:String){
        Log.d("PureBBS", "new category current: $idStr")
        categoryCurrent = idStr
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