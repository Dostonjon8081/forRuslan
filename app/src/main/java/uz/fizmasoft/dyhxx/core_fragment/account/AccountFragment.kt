package uz.fizmasoft.dyhxx.core_fragment.account

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import uz.fizmasoft.dyhxx.BuildConfig
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentAccountBinding
import uz.fizmasoft.dyhxx.helper.util.TELEGRAM_FEEDBACK_URL
import uz.fizmasoft.dyhxx.helper.util.TELEGRAM_FEEDBACK_URL_CHROME
import uz.fizmasoft.dyhxx.helper.util.getPref
import uz.fizmasoft.dyhxx.helper.util.logd


@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate){
/*
    private val viewModel: AccountViewModel by activityViewModels()
    private var pref: SharedPreferences? = null

    private lateinit var reviewInfo: ReviewInfo
    private lateinit var manager: ReviewManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = getPref(requireActivity())
        setViews()
        manager = ReviewManagerFactory.create(requireContext())
    }

    private fun setViews() {
        binding.accountFragmentImg.setOnClickListener { openSomeActivityForResult() }
        binding.accountFragmentLanguage.setOnClickListener { showLanguages() }
        binding.accountFragmentLogout.setOnClickListener { logOutAlert() }
        binding.aboutApp.setOnClickListener { aboutApp() }
        binding.accountFeedBack.setOnClickListener { feedBack() }
        binding.accountRate.setOnClickListener { showRateApp() }
        binding.accountAppVersion.text = "version: ${BuildConfig.VERSION_NAME}"
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                binding.accountFragmentImg.setImageURI(data?.data)
            }
        }

    private fun openSomeActivityForResult() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    private fun rateApp() {


        val request: Task<ReviewInfo?> = manager.requestReviewFlow()
        request.addOnCompleteListener { task: Task<ReviewInfo?> ->
            logd(task.isSuccessful)
            if (task.isSuccessful) {
                // We can get the ReviewInfo object
                val reviewInfo = task.result
                logd(reviewInfo)
                val flow =
                    manager.launchReviewFlow(requireActivity(), reviewInfo)
                flow.addOnCompleteListener { }
            } else {

            }
        }
    }

    private fun aboutApp() {
        getBaseActivity {
//            it.navController?.navigate(AccountFragmentDirections.actionAccountFragmentToAboutFragment())
        }
    }

    private fun logOutAlert() {
        val builder = AlertDialog.Builder(requireContext())
            .setMessage(getString(R.string.log_out_massege))
            .setPositiveButton(R.string.yes) { _, _ ->
                logOut()
            }
            .setNegativeButton(R.string.no) { _, _ ->
            }
        builder.create()
        builder.show()
    }

    private fun logOut() {
        getBaseActivity {
            getPref(it).edit().clear().apply()
            viewModel.deleteAll()
            it.navController?.navigate(R.id.select_language_fragment)
//            (it as uz.fizmasoft.dyhxx.activity.MainActivity).binding.idBottomNavigation.visibility =
//                View.GONE
        }
    }

    private fun showLanguages() {
        AccountLanguageFragment().show(requireActivity().supportFragmentManager, "language")
    }

    private fun feedBack() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_FEEDBACK_URL)))
        } catch (e: Exception) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_FEEDBACK_URL_CHROME)))
        }
    }

    private fun showRateApp() {

        val request: Task<ReviewInfo?> = manager.requestReviewFlow()
        request.addOnCompleteListener { task: Task<ReviewInfo?> ->
            if (task.isSuccessful) {
//                showRateAppFallbackDialog()
                val reviewInfo = task.result
                val flow: Task<Void?> =
                    manager.launchReviewFlow(requireActivity(), reviewInfo)
                flow.addOnCompleteListener { _: Task<Void?>? -> }
            } else {
                showRateAppFallbackDialog()
            }
        }
    }

    private fun showRateAppFallbackDialog() {

        AlertDialog.Builder(requireContext())
            .setTitle("Rate App")
            .setPositiveButton("Yes") { _, _ -> redirectToPlayStore() }
            .setNegativeButton(
                "No"
            ) { _, _ -> }
            .setNeutralButton(
                "Cancel"
            ) { _, _ -> }
            .setOnDismissListener(DialogInterface.OnDismissListener { })
            .show()
    }

    private fun redirectToPlayStore() {
        val appPackageName: String = requireActivity().packageName
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (exception: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }
    */
 */

}