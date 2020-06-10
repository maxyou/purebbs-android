package com.maxproj.purebbs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
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
import com.maxproj.purebbs.config.ConfigViewModel
import com.maxproj.purebbs.config.ConfigViewModelFactory
import com.maxproj.purebbs.net.HttpData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.HttpException
import com.maxproj.purebbs.net.HttpApi
import com.maxproj.purebbs.net.HttpService
import com.maxproj.purebbs.post.PostViewModel
import com.maxproj.purebbs.post.PostViewModelFactory

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this, ConfigViewModelFactory(this.application, HttpService.api))
            .get(ConfigViewModel::class.java)
    }

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

        viewModel.adapter.lifecycleOwner = this
        category.adapter = viewModel.adapter

    }

    private fun initCategory(){




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
