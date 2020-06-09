package com.maxproj.purebbs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.maxproj.purebbs.config.CategoryAdapter
import com.maxproj.purebbs.config.Config
import com.maxproj.purebbs.net.HttpData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.HttpException
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.net.HttpService

class MainActivity : AppCompatActivity() {
    val adapter = CategoryAdapter()
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
        val navController = host.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.post_dest),
            drawer_layout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        nav_view.setupWithNavController(navController)

        initCategory()
    }

    private fun initCategory(){

        adapter.lifecycleOwner = this
        val mock = (0..5).map { Config.Category("aaa","bbb") }
        adapter.submitList(mock)
        category.adapter = adapter

        Log.d("PureBBS", "<initCategory>")

        lifecycleScope.launch {
            var data: HttpData.CategoryListRet
            try {
                Log.d("PureBBS", "<initCategory> before httpApi.getCategoryList")
                data = HttpService.api.getCategoryList()
                Log.d("PureBBS", "<initCategory> after httpApi.getCategoryList")
            }catch (he: HttpException){
                Log.d("PureBBS", "<initCategory> catch HttpException")
                Log.d("PureBBS", he.toString())
                return@launch
            }catch (throwable:Throwable){
                Log.d("PureBBS", "<initCategory> catch Throwable")
                Log.d("PureBBS", throwable.toString())
                return@launch
            }

            adapter.submitList(data.data.category)
            Config.categories = data.data.category
            Config.categoryCurrent = null
            Log.d("PureBBS", "<initCategory> categories:${Config.categories}")
        }


//        runBlocking{
//            coroutineScope{
//            }
//            withContext(Dispatchers.IO){
//            }
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
//        // Allows NavigationUI to support proper up navigation or the drawer layout
//        // drawer menu, depending on the situation
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }
}
