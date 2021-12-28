package uz.fizmasoft.dyhxx.main_fragment

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import dagger.hilt.android.AndroidEntryPoint
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentMainBinding

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate), IFabState {
//    private val navView: BottomNavigationView = binding.bottomNavigationView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor: View = requireActivity().window.decorView
            requireActivity().window.statusBarColor =
                resources.getColor(R.color.app_background_color, null)
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }


        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(1).isEnabled = false

        getBaseActivity {
            it.bottomNavController = Navigation.findNavController(
                requireActivity(),
                R.id.container_main_fragment_navigation
            )
            NavigationUI.setupWithNavController(
                binding.bottomNavigationView,
                it.bottomNavController!!
            )
        }
    }

    override fun fabState(isPlus: Boolean) {
        if (isPlus) {
            binding.fab.setImageDrawable(resources.getDrawable(R.drawable.ic_plus, null))
        } else {
            binding.fab.setImageDrawable(resources.getDrawable(R.drawable.ic_qver, null))
        }
    }
}