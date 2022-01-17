package uz.fizmasoft.dyhxx.violation.violation_detail

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentViolationBinding
import uz.fizmasoft.dyhxx.databinding.FragmentViolationDetailBinding

@AndroidEntryPoint
class ViolationDetailFragment:BaseFragment<FragmentViolationDetailBinding>(FragmentViolationDetailBinding::inflate) {

    private val arg:ViolationDetailFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor: View = requireActivity().window.decorView
            requireActivity().window.statusBarColor =
                resources.getColor(R.color.violation_detail_light_blue_color, null)
//            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }
}