package uz.fizmasoft.dyhxx.init

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentAuthBinding
import uz.fizmasoft.dyhxx.helper.util.PREF_TOKEN_KEY
import uz.fizmasoft.dyhxx.helper.util.TELEGRAM_AUTH_URL
import uz.fizmasoft.dyhxx.helper.util.getPref
import dagger.hilt.android.AndroidEntryPoint
import uz.fizmasoft.dyhxx.helper.util.carToast


@AndroidEntryPoint
class AuthFragment : BaseFragment<FragmentAuthBinding>(FragmentAuthBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.idAuthFragmentButtonTelegram.setOnClickListener {

            try {

                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(TELEGRAM_AUTH_URL)))
            } catch (e:Exception){
                carToast(requireContext(),"Sizda telegram mavjud emas")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (!getPref(requireActivity()).getString(PREF_TOKEN_KEY, "").isNullOrEmpty()) {
//            (activity as MainActivity).navController?.navigate(R.id.action_authFragment_to_homeFragment)
        }
    }



}