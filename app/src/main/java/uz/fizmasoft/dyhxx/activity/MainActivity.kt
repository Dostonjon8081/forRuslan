package uz.fizmasoft.dyhxx.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseActivity
import uz.fizmasoft.dyhxx.core_fragment.home.HomeViewModel
import uz.fizmasoft.dyhxx.databinding.ActivityMainBinding
import uz.fizmasoft.dyhxx.helper.db.CarEntity
import uz.fizmasoft.dyhxx.helper.network.model.AllCars
import uz.fizmasoft.dyhxx.helper.util.*


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
            PDFUtils.checkStoragePermission(this)

            CoroutineScope(Dispatchers.IO).async {
                editPref.putString(PREF_TOKEN_KEY, intent.data.toString().substring(22))
                    .commit()
            }.onAwait
            loadDataFromApi()
        }
    }

    private fun loadDataFromApi() {
        val editPref = getPref(this@MainActivity).edit()
        viewModel.getUserIdApi(getPref(this).getString(PREF_TOKEN_KEY, "")!!)
        viewModel.responseUserIdApi.observe(this) {
            try {
                editPref.putString(PREF_USER_ID_KEY, it.data?.user_id.toString()).commit()

                viewModel.allCarsApi(AllCars(getPref(this).getString(PREF_USER_ID_KEY, "")!!))
                viewModel.responseAllCarsApi.observe(this, EventObserver { data ->
//                    val listCars = mutableListOf<CarEntity>()

                    if (data.data!!.data != null && data.data.status != 404) {
                        for (item in data.data.data) {
//                            listCars.add(CarEntity(0, item.passport, item.tex_passport))
                            viewModel.insertCarDB(
                                CarEntity(
                                    0,
                                    item.passport,
                                    item.tex_passport
                                )
                            )
                        }

                    }
                })
            } catch (e: Exception) {
                carToast(this, e.toString())
            }
        }

    }

    fun setLocale(language: String = "uz") {
        LocaleHelper.setLocale(this, language)
    }

}