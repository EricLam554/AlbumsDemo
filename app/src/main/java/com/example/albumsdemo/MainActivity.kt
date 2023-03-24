package com.example.albumsdemo

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewbinding.BuildConfig
import com.example.albumsdemo.R
import com.example.albumsdemo.databinding.ActivityMainBinding
import com.example.albumsdemo.presentation.home.AlbumsDetailFragment
import com.example.albumsdemo.presentation.home.AlbumsListItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNavigation()
    }

    private fun setNavigation() {
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_bookmark
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

//    fun goToAlbumsDetail(albumsListItemViewModel: AlbumsListItemViewModel) {
//        val albumsDetailFragment = AlbumsDetailFragment()
//        val arguments = Bundle()
//
//        albumsDetailFragment.arguments = arguments
//
//        supportFragmentManager.beginTransaction()
//            .add(R.id.root_view, albumsDetailFragment, null)
//            .addToBackStack(null).commit()
//    }

}