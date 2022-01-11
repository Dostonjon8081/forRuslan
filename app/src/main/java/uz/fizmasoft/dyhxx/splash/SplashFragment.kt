package uz.fizmasoft.dyhxx.splash

import android.os.*
import android.view.View
import android.view.WindowManager
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentSplashBinding
import uz.fizmasoft.dyhxx.helper.util.getPref
import dagger.hilt.android.AndroidEntryPoint
import uz.fizmasoft.dyhxx.helper.util.PREF_TOKEN_KEY

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        activity?.window?.statusBarColor =
//            ContextCompat.getColor(requireContext(), R.color.toolbar_color)
        requireActivity().window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = requireContext().resources.getColor(R.color.transparent)
        }

        Handler(Looper.getMainLooper()).postDelayed({
            try {
                getBaseActivity {
                    if (getPref(requireActivity()).getString(PREF_TOKEN_KEY, "")
                            .isNullOrEmpty()
                    ) {
//                        if (Build.VERSION == Build.VERSION_CODES)
                        it.navController?.navigate(R.id.action_splashFragment_to_selectLanguageFragment)
                    } else {
                        it.navController?.navigate(R.id.action_splash_fragment_to_mainFragment)
                    }
                }
            } catch (e: Exception) {

            }
        }, 2000)
    }

}