package uz.fizmasoft.dyhxx.splash

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentSplashBinding
import uz.fizmasoft.dyhxx.helper.util.PREF_USER_ID_KEY
import uz.fizmasoft.dyhxx.helper.util.getPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        activity?.window?.statusBarColor =
//            ContextCompat.getColor(requireContext(), R.color.toolbar_color)

        Handler(Looper.getMainLooper()).postDelayed({
            try {
                getBaseActivity {
//getPref(requireActivity()).edit().putString(PREF_USER_ID_KEY,"1638094110").commit()
                    if (getPref(requireActivity()).getString(PREF_USER_ID_KEY, "")
                            .isNullOrEmpty()
                    ) {
//                        if (Build.VERSION == Build.VERSION_CODES)
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