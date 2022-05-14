package uz.fizmasoft.dyhxx.activity

//import com.google.firebase.analytics.FirebaseAnalytics
//import com.google.firebase.analytics.ktx.analytics
//import com.google.firebase.ktx.Firebase
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
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
import uz.fizmasoft.dyhxx.helper.network.model.AllCarsResponseModel
import uz.fizmasoft.dyhxx.helper.util.*
import java.util.concurrent.Executors


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

        appUpdateManager = AppUpdateManagerFactory.create(this)
        checkUpdate()

        if (listener != null) {
            appUpdateManager.registerListener(listener)
        }

        setLocale(LocaleHelper.getLanguage(this))

        navController = findNavController(R.id.container_main_navigation)

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
        if (isOnline(this)) {

            val editPref = getPref(this@MainActivity).edit()
            val number = if (intent?.data.toString().startsWith("https://autoinfo.uz")) 26
            else 19
            CoroutineScope(Dispatchers.IO).launch {
                editPref.putString(PREF_TOKEN_KEY, intent.data.toString().substring(number))
                    .apply()
            }

            logd(intent.data.toString().substring(number))
            loadDataFromApi(intent.data.toString().substring(number))
        }
    }

    private fun loadDataFromApi(token: String) {
        viewModel.allCarsApi(token)
        viewModel.responseAllCarsApi.observe(this, EventObserver { result ->
            when (result) {
                is NetworkResult.Loading -> {
                }
                is NetworkResult.Success -> {
                    insertToDb(result)
                }
                is NetworkResult.Error -> {
                }
            }
        })

    }

    private fun insertToDb(result: NetworkResult.Success<List<AllCarsResponseModel>>) {
        viewModel.allCarsDB()
        viewModel.allCarDB.observe(this) { dbList ->
            if (dbList.isEmpty()) {
                Executors.newSingleThreadExecutor().execute {
                    if (result.data?.isNotEmpty()!!) {
                        for (resultItem in result.data) {
                            viewModel.insertCarDB(
                                CarEntity(
                                    0,
                                    resultItem.car_number,
                                    resultItem.tex_passport
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun setLocale(language: String = "uz") {
        LocaleHelper.setLocale(this, language)
    }

    private fun checkUpdate() {
        val appUpdateInfoTask = appUpdateManager?.appUpdateInfo

        appUpdateInfoTask?.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {

                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    this,
                    IN_APP_UPDATE_REQUEST_CODE
                )
            } else {
            }
        }
        appUpdateInfoTask?.addOnFailureListener {

        }
    }

    private fun updateInProgress() {
        appUpdateManager?.appUpdateInfo?.addOnSuccessListener { updateInfo ->
            if (updateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                appUpdateManager?.startUpdateFlowForResult(
                    updateInfo, AppUpdateType.FLEXIBLE, this,
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