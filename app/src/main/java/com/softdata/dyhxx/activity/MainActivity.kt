package com.softdata.dyhxx.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.softdata.dyhxx.R
import com.softdata.dyhxx.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    var navController: NavController? = null
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.container_main_navigation)

        val navView: BottomNavigationView = binding.idBottomNavigation

//        val navControllerBottom = findNavController(R.id.container_main_navigation)
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.home_fragment,
//                R.id.notification_fragment,
//                R.id.account_fragment
//            )
//        )
//        setupActionBarWithNavController(navController!!, appBarConfiguration)
        navView.setupWithNavController(navController!!)
    }


}