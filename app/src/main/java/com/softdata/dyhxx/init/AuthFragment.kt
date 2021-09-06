package com.softdata.dyhxx.init

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.softdata.dyhxx.R
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.base.BaseFragment
import com.softdata.dyhxx.databinding.FragmentAuthBinding
import com.softdata.dyhxx.helper.util.PREF_TOKEN_KEY
import com.softdata.dyhxx.helper.util.TELEGRAM_AUTH_URL
import com.softdata.dyhxx.helper.util.getPref
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.idAuthFragmentButtonTelegram.setOnClickListener {

            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_AUTH_URL)))
        }
    }

    override fun onStart() {
        super.onStart()
        if (!getPref(requireActivity()).getString(PREF_TOKEN_KEY, "").isNullOrEmpty()) {
            (activity as MainActivity).navController?.navigate(R.id.action_authFragment_to_homeFragment)
        }
    }



}