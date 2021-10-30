package uz.fizmasoft.dyhxx.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseActivity
import uz.fizmasoft.dyhxx.core_fragment.home.HomeViewModel
import uz.fizmasoft.dyhxx.databinding.ActivityMainBinding
import uz.fizmasoft.dyhxx.helper.db.CarEntity
import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.network.model.AllCars
import uz.fizmasoft.dyhxx.helper.util.*


@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var appUpdateManager: AppUpdateManager
    private val listener: InstallStateUpdatedListener? =
        InstallStateUpdatedListener { installState ->
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
//            showSnackBarForCompleteUpdate()
            }
        }

    override fun setupItems() {
        fireBaseAnalytics()

        setActivity(this)
//        logd(getPrefActive().getString(PREF_USER_ID_KEY,""))
        appUpdateManager = AppUpdateManagerFactory.create(this)
        checkUpdate()

        if (listener != null) {
            appUpdateManager.registerListener(listener)
        }

        setLocale(LocaleHelper.getLanguage(this))

        navController = findNavController(R.id.container_main_navigation)
        val navView = binding.idBottomNavigation
        navController?.let { navView.setupWithNavController(it) }

        if (intent?.data != null) {
            authentication(intent)
        }

    }

    private fun fireBaseAnalytics() {
        firebaseAnalytics = Firebase.analytics
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "MainActivity")
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)

    }

    override fun onResume() {
        super.onResume()
        updateInProgress()
    }

    private fun authentication(intent: Intent) {
        if (getPref(this).getString(PREF_TOKEN_KEY, "").isNullOrEmpty()
            && isOnline(this)
        ) {

            val editPref = getPref(this@MainActivity).edit()
            CoroutineScope(Dispatchers.IO).launch {
                editPref.putString(PREF_TOKEN_KEY, intent.data.toString().substring(19))
                    .apply()
            }
            loadDataFromApi(intent.data.toString().substring(19))
        }
    }

    private fun loadDataFromApi(token: String) {
        val editPref = getPref(this@MainActivity).edit()
        viewModel.getUserIdApi(token)
        viewModel.responseUserIdApi.observe(this) {
            try {

                when (it) {
                    is NetworkResult.Success -> {

                        editPref.putString(PREF_USER_ID_KEY, it.data?.user_id.toString()).apply()
                        viewModel.allCarsApi(AllCars(it.data?.user_id.toString()))
                        viewModel.responseAllCarsApi.observe(this, EventObserver { data ->

                            when (data) {
                                is NetworkResult.Success -> {
                                    if (data.data?.data != null && data.data.status != 404) {
                                        for (item in data.data.data) {
                                            viewModel.insertCarDB(
                                                CarEntity(0, item.passport, item.tex_passport)
                                            )
                                        }

                                    }
                                }
                                is NetworkResult.Error -> {
                                }
                                is NetworkResult.Loading -> {
                                }
                            }

                        })
                    }
                    is NetworkResult.Error -> {
                        carToast(this, "Ro'yhatdan o'tish amalga oshmadi. Iltimos qaytadan o'ting")
                    }
                    is NetworkResult.Loading -> {
                        carToast(this, "Loading...")
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


    private fun checkUpdate() {
        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager?.appUpdateInfo
        // Checks that the platform will allow the specified type of update.

        appUpdateInfoTask?.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {

                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    this,
                    IN_APP_UPDATE_REQUEST_CODE
                )
            } else {
//                logd("No Update available")
            }
        }
        appUpdateInfoTask?.addOnFailureListener {
//            carToast(this, "No updated")
        }
    }

    private fun updateInProgress() {
        appUpdateManager?.appUpdateInfo?.addOnSuccessListener { updateInfo ->
            if (updateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                appUpdateManager?.startUpdateFlowForResult(
                    updateInfo, AppUpdateType.IMMEDIATE, this,
                    IN_APP_UPDATE_REQUEST_CODE
                )
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IN_APP_UPDATE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    carToast(this, "Update done")
                }
                Activity.RESULT_CANCELED -> {
                    carToast(this, "Update Canceled")
                }
                ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> {
                    carToast(this, "Fail Update")
                }
            }
        }
    }
}