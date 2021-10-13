package uz.fizmasoft.dyhxx.init

import android.os.Bundle
import android.view.View
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentSelectLanguageBinding
import uz.fizmasoft.dyhxx.helper.util.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class SelectLanguageFragment :
    BaseFragment<FragmentSelectLanguageBinding>(FragmentSelectLanguageBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.idButtonUz.setOnClickListener(this::onSelectLanguage)
        binding.idButtonRu.setOnClickListener(this::onSelectLanguage)
        binding.idButtonEn.setOnClickListener(this::onSelectLanguage)

    }


    private fun onSelectLanguage(v: View) {
        when (v.id) {
            R.id.id_button_uz ->  LocaleHelper.setLocale(requireActivity(),"uz")
            R.id.id_button_ru ->  LocaleHelper.setLocale(requireActivity(),"ru")
            R.id.id_button_en ->  LocaleHelper.setLocale(requireActivity(),"en")
        }

        getBaseActivity {
            it.navController?.navigate(R.id.action_selectLanguageFragment_to_authFragment)
        }
    }
    
}