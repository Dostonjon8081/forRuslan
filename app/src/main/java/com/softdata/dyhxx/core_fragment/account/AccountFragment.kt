package com.softdata.dyhxx.core_fragment.account

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.softdata.dyhxx.base.BaseFragment
import com.softdata.dyhxx.databinding.FragmentAccountBinding
import com.softdata.dyhxx.helper.util.PREF_LANG_KEY
import com.softdata.dyhxx.helper.util.getPref
import com.softdata.dyhxx.helper.util.logd
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {


    private var pref: SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = getPref(requireActivity())
        setViews()
    }

    private fun setViews() {
        binding.accountFragmentLanguage.setOnClickListener { showLanguages() }
    }

    private fun showLanguages() {
        AccountLanguageFragment().show(requireActivity().supportFragmentManager, "language")
    }
}