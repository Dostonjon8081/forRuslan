package com.softdata.dyhxx.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat
import com.softdata.dyhxx.R
import com.softdata.dyhxx.base.BaseFragment
import com.softdata.dyhxx.databinding.FragmentSplashBinding
import com.softdata.dyhxx.helper.util.PREF_USER_ID_KEY
import com.softdata.dyhxx.helper.util.getPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        activity?.window?.statusBarColor =
//            ContextCompat.getColor(requireContext(), R.color.toolbar_color)

        Handler().postDelayed({
            try {
                getBaseActivity {

                    if (getPref(requireActivity()).getString(PREF_USER_ID_KEY, "")
                            .isNullOrEmpty()
                    ) {
                        it.navController?.navigate(R.id.action_splashFragment_to_selectLanguageFragment)
                    } else {
                        it.navController?.navigate(R.id.action_splashFragment_to_homeFragment)
                    }
                }
            } catch (e: Exception) {

            }
        }, 2000)
    }

}