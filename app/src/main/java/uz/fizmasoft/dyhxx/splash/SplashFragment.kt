package uz.fizmasoft.dyhxx.splash

import android.os.Bundle
import android.os.Handler
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