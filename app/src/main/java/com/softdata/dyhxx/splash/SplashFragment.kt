package com.softdata.dyhxx.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.core.content.ContextCompat
import com.softdata.dyhxx.R
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.base.BaseFragment
import com.softdata.dyhxx.databinding.FragmentSplashBinding
import com.softdata.dyhxx.helper.util.PREF_USER_ID_KEY
import com.softdata.dyhxx.helper.util.getPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

//    var navController: NavController? = null

//    private var _binding: FragmentSplashBinding? = null
//    private val binding get() = _binding!!

    /*   override fun onCreateView(
           inflater: LayoutInflater,
           container: ViewGroup?,
           savedInstanceState: Bundle?
       ): View? {
           _binding = FragmentSplashBinding.inflate(inflater, container, false)
           return binding.root
       }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.bottom_select_color)

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


    /*  override fun onDetach() {
          super.onDetach()
          _binding = null
      }*/
}