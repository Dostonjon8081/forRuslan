package com.softdata.dyhxx.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.softdata.dyhxx.R
import com.softdata.dyhxx.core_fragment.account.AccountFragment
import com.softdata.dyhxx.databinding.ActivityMainBinding
import com.softdata.dyhxx.helper.db.CarEntity
import com.softdata.dyhxx.helper.db.carViewModel.CarViewModel
import com.softdata.dyhxx.helper.network.NetworkResult
import com.softdata.dyhxx.helper.network.model.AllCars
import com.softdata.dyhxx.helper.network.viewModel.ApiViewModel
import com.softdata.dyhxx.helper.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val apiViewModel: ApiViewModel by viewModels()
    private val carViewModel: CarViewModel by viewModels()

    var navController: NavController? = null
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.container_main_navigation)
        val navView: BottomNavigationView = binding.idBottomNavigation
        navView.setupWithNavController(navController!!)
//        navView.menu.findItem(R.id.account_fragment).isCheckable = false

        if (intent.data != null) {
            authentication(intent)
        }

/*        navView.setOnItemSelectedListener(
            object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(item: MenuItem): Boolean {
                    when (item.itemId) {
                        R.id.account_fragment -> AccountFragment().show(supportFragmentManager,"ACCOUNT")
                        R.id.notification_fragment -> navController!!.navigate(R.id.notification_fragment)
                        R.id.home_fragment -> navController!!.navigate(R.id.home_fragment)
                    }
                    return true
                }
            }
        )*/

    }

    private fun authentication(intent: Intent) {
        if (getPref(this).getString(PREF_TOKEN_KEY, "").isNullOrEmpty()
            && isOnline(this)
        ) {
            val editPref = getPref(this@MainActivity).edit()

            CoroutineScope(Dispatchers.IO).async {
                editPref.putString(PREF_TOKEN_KEY, intent.data.toString().substring(22))
                    .commit()
            }.onAwait

            apiViewModel.getUserId(getPref(this).getString(PREF_TOKEN_KEY, "")!!)
            apiViewModel.responseUserId.observe(this) {
                try {
                    editPref.putString(PREF_USER_ID_KEY, it.data?.user_id.toString()).commit()

                    apiViewModel.allCars(AllCars(getPref(this).getString(PREF_USER_ID_KEY, "")!!))
                    apiViewModel.responseAllCars.observe(this) { data ->
                        val listCars = mutableListOf<CarEntity>()

                        if (data.data != null && data.data.status != 404) {
                            for (item in data.data.data) {
                                listCars.add(CarEntity(0, item.passport, item.tex_passport))
                                carViewModel.insertCar(
                                    CarEntity(
                                        0,
                                        item.passport,
                                        item.tex_passport
                                    )
                                )
                            }
                        }
                    }
                } catch (e: Exception) {
                    carToast(this, e.toString())
                }
                Log.d("AllIntent", it.data.toString())
            }

        }
    }


}