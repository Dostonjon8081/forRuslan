package uz.fizmasoft.dyhxx.core_fragment.account

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.fizmasoft.dyhxx.BuildConfig
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentAccountBinding
import uz.fizmasoft.dyhxx.helper.util.TELEGRAM_FEEDBACK_URL
import uz.fizmasoft.dyhxx.helper.util.TELEGRAM_FEEDBACK_URL_CHROME
import uz.fizmasoft.dyhxx.helper.util.getPref


@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {

    private val viewModel: AccountViewModel by activityViewModels()
    private var pref: SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = getPref(requireActivity())
        setViews()
    }

    private fun setViews() {
        binding.accountFragmentLanguage.setOnClickListener { showLanguages() }
        binding.accountFragmentLogout.setOnClickListener { logOutAlert() }
        binding.aboutApp.setOnClickListener { aboutApp() }
        binding.accountFeedBack.setOnClickListener { feedBack() }
        binding.accountAppVersion.text = "version: ${BuildConfig.VERSION_NAME}"
    }

    private fun aboutApp() {
        getBaseActivity {
            it.navController?.navigate(AccountFragmentDirections.actionAccountFragmentToAboutFragment())
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
            (it as uz.fizmasoft.dyhxx.activity.MainActivity).binding.idBottomNavigation.visibility =
                View.GONE
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

}