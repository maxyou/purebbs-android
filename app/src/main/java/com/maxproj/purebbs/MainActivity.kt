package com.maxproj.purebbs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.maxproj.purebbs.config.Config
import com.maxproj.purebbs.config.ConfigViewModel
import com.maxproj.purebbs.config.ConfigViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import com.maxproj.purebbs.net.HttpService

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

        viewModel.categoryAdapter.lifecycleOwner = this
        viewModel.categoryAdapter.viewModel = viewModel
        category.adapter = viewModel.categoryAdapter
        viewModel.categoryList?.observe(this, Observer {
            Log.d("PureBBS", "Observed postList onChange")
            viewModel.categoryAdapter.submitList(it)
        })
        Config.categoryCurrentLive.observe(this, Observer {
            viewModel.currentCategoryChanged(it)
        })
        viewModel.updateCategoryList()
        viewModel.mDrawerLayout = drawer_layout
    }

    override fun onSupportNavigateUp(): Boolean {
//        // Allows NavigationUI to support proper up navigation or the drawer layout
//        // drawer menu, depending on the situation
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }
}
