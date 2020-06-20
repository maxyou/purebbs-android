package com.maxproj.purebbs.config

import android.app.Application
import android.util.Log
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.*
import androidx.navigation.Navigation
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.post.*

class ConfigViewModel (application: Application, httpApi: HttpApi) : AndroidViewModel(application) {

    private var configRepository: ConfigRepository
    val categoryList: LiveData<List<Category>>?
    val configDao:ConfigDao = MyRoomDatabase.getDatabase(application, viewModelScope).configDao()
    val postDao:PostDao = MyRoomDatabase.getDatabase(application, viewModelScope).postDao()
    lateinit var mDrawerLayout:DrawerLayout

    init {
        configRepository = ConfigRepository(viewModelScope, configDao, httpApi)
        categoryList = configRepository.categoryList
    }

    val categoryAdapter = CategoryAdapter()

    fun updateCategoryList(){
        configRepository.updateCategoryList()
    }
    fun updateCategoryCurrent(view: View, idStr:String){
        Log.d("PureBBS", "<category refresh> category current, old:${Config._categoryCurrentLive.value} new: $idStr")
        Config._categoryCurrentLive.value = idStr

        mDrawerLayout.closeDrawers()
    }
    fun currentCategoryChanged(it: String) {
        categoryAdapter.notifyDataSetChanged()
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