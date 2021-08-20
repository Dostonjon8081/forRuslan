package com.softdata.dyhxx.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.softdata.dyhxx.R
import com.softdata.dyhxx.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    lateinit var viewModel: CarViewModel

    var navController: NavController? = null
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        viewModel = ViewModelProvider(this).get(CarViewModel::class.java)
        navController = findNavController(R.id.container_main_navigation)

        val navView: BottomNavigationView = binding.idBottomNavigation

        navView.setupWithNavController(navController!!)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode==1001){
            Toast.makeText(this,"$data", Toast.LENGTH_SHORT).show()
        }
    }


}