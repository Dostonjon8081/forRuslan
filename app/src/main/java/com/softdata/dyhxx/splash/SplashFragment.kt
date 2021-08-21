package com.softdata.dyhxx.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.ActivityNavigatorExtras
import com.softdata.dyhxx.R
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.databinding.FragmentSplashBinding
import com.softdata.dyhxx.helper.util.FIRST_INIT
import com.softdata.dyhxx.helper.util.getPref
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.util.Pair as UtilPair

@AndroidEntryPoint
class SplashFragment : Fragment() {

//    var navController: NavController? = null

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.bottom_select_color)

        Handler().postDelayed({
            if (getPref(requireActivity()).getBoolean(FIRST_INIT, true)) {
                (activity as MainActivity).navController!!.navigate(R.id.action_splashFragment_to_selectLanguageFragment)
            } else {

                (activity as MainActivity).navController!!.navigate(R.id.action_splashFragment_to_homeFragment)
            }
        }, 2000)
    }
}