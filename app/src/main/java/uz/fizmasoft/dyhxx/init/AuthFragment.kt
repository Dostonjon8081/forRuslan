package uz.fizmasoft.dyhxx.init

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.activity.MainActivity
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentAuthBinding
import uz.fizmasoft.dyhxx.helper.util.PREF_TOKEN_KEY
import uz.fizmasoft.dyhxx.helper.util.TELEGRAM_AUTH_URL
import uz.fizmasoft.dyhxx.helper.util.getPref
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
//            (activity as MainActivity).navController?.navigate(R.id.action_authFragment_to_homeFragment)
        }
    }



}