package com.softdata.dyhxx.core_fragment.account

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.softdata.dyhxx.R
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.base.BaseFragment
import com.softdata.dyhxx.databinding.FragmentAccountBinding
import com.softdata.dyhxx.helper.util.getPref
import dagger.hilt.android.AndroidEntryPoint


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
            (it as MainActivity).binding.idBottomNavigation.visibility = View.GONE
        }
    }

    private fun showLanguages() {
        AccountLanguageFragment().show(requireActivity().supportFragmentManager, "language")
    }
}