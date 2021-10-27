package uz.fizmasoft.dyhxx.core_fragment.account.about_app

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import dagger.hilt.android.AndroidEntryPoint
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentAboutBinding

@AndroidEntryPoint
class AboutFragment : BaseFragment<FragmentAboutBinding>(FragmentAboutBinding::inflate){

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                getBaseActivity {
                    it.navController?.popBackStack()
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.aboutFragmentArrowBack.setOnClickListener { activity?.onBackPressed() }
    }
}