package uz.fizmasoft.dyhxx.init

import android.os.Build
import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentSelectLanguageBinding
import uz.fizmasoft.dyhxx.helper.util.LocaleHelper


@AndroidEntryPoint
class SelectLanguageFragment :
    BaseFragment<FragmentSelectLanguageBinding>(FragmentSelectLanguageBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor: View = requireActivity().window.decorView
            requireActivity().window.statusBarColor = resources.getColor(R.color.app_background_color,null)
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        binding.idButtonUz.setOnClickListener(this::onSelectLanguage)
        binding.idButtonRu.setOnClickListener(this::onSelectLanguage)
        binding.idButtonEn.setOnClickListener(this::onSelectLanguage)

    }


    private fun onSelectLanguage(v: View) {
        when (v.id) {
            R.id.id_button_uz -> LocaleHelper.setLocale(requireActivity(), "uz")
            R.id.id_button_ru -> LocaleHelper.setLocale(requireActivity(), "ru")
            R.id.id_button_en -> LocaleHelper.setLocale(requireActivity(), "en")
        }

        getBaseActivity {
            it.navController?.navigate(R.id.action_selectLanguageFragment_to_authFragment)
        }
    }

}