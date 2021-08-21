package com.softdata.dyhxx.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.softdata.dyhxx.R
import com.softdata.dyhxx.databinding.ActivityMainBinding
import com.softdata.dyhxx.helper.db.carViewModel.CarViewModel
import com.softdata.dyhxx.helper.network.NetworkResult
import com.softdata.dyhxx.helper.network.model.CheckLimitModel
import com.softdata.dyhxx.helper.network.viewModel.ApiViewModel
import com.softdata.dyhxx.helper.util.PREF_TOKEN_KEY
import com.softdata.dyhxx.helper.util.getPref
import com.softdata.dyhxx.helper.util.loge
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    lateinit var viewModel: CarViewModel

    private val viewModel: ApiViewModel by viewModels()
    var navController: NavController? = null
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.container_main_navigation)
        val navView: BottomNavigationView = binding.idBottomNavigation
        navView.setupWithNavController(navController!!)

        val intent = intent

        if (intent.data != null) {
            CoroutineScope(Dispatchers.IO).async {
                getPref(this@MainActivity).edit().putString(PREF_TOKEN_KEY, intent.data.toString().substring(22))
                    .commit()
            }.onAwait

            viewModel.getUserId(getPref(this).getString(PREF_TOKEN_KEY,"")!!)
            viewModel.responseUserId.observe(this) {

                Log.d("AllIntent", it.data.toString())
                loge(it.toString())
               /* when (it) {
                    is NetworkResult.Success -> {
                        loge(it.data?:"")
                    }
                    is NetworkResult.Error -> {
                        Log.d("getIntent", "${it.data}")
                    }
                    is NetworkResult.Loading -> {
                        // show a progress bar
                    }
                }*/
            }

            viewModel.checkLimit(CheckLimitModel("615155753"))
            viewModel.responseCheckLimit.observe(this, Observer {
                Log.d("AllIntent", it.data.toString())
            })
        }

    }
}