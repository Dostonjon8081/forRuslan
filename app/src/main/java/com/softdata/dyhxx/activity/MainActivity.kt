package com.softdata.dyhxx.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.softdata.dyhxx.R
import com.softdata.dyhxx.base.BaseActivity
import com.softdata.dyhxx.core_fragment.home.HomeViewModel
import com.softdata.dyhxx.databinding.ActivityMainBinding
import com.softdata.dyhxx.helper.db.CarEntity
import com.softdata.dyhxx.helper.network.model.AllCars
import com.softdata.dyhxx.helper.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()


    override fun setupItems() {
        setLocale(LocaleHelper.getLanguage(this))

        navController = findNavController(R.id.container_main_navigation)
        val navView = binding.idBottomNavigation
        navView.setupWithNavController(navController!!)

        if (intent.data != null) {
            authentication(intent)
        }

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

            loadDataFromApi()
        }
    }

    fun loadDataFromApi(){
        val editPref = getPref(this@MainActivity).edit()
        viewModel.getUserIdApi(getPref(this).getString(PREF_TOKEN_KEY, "")!!)
        viewModel.responseUserIdApi.observe(this) {
            try {
                editPref.putString(PREF_USER_ID_KEY, it.data?.user_id.toString()).commit()

                viewModel.allCarsApi(AllCars(getPref(this).getString(PREF_USER_ID_KEY, "")!!))
                viewModel.responseAllCarsApi.observe(this) { data ->
                    val listCars = mutableListOf<CarEntity>()

                    if (data.data != null && data.data.status != 404) {
                        for (item in data.data.data) {
                            listCars.add(CarEntity(0, item.passport, item.tex_passport))
                            viewModel.insertCarDB(
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
        }

    }

    fun setLocale(language: String = "uz") {
        LocaleHelper.setLocale(this, language)
    }

}