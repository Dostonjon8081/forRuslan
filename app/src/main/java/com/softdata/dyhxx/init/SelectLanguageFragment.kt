package com.softdata.dyhxx.init

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.softdata.dyhxx.R
import com.softdata.dyhxx.base.BaseFragment
import com.softdata.dyhxx.databinding.FragmentSelectLanguageBinding
import com.softdata.dyhxx.helper.util.PREF_LANG_KEY
import com.softdata.dyhxx.helper.util.getPref
import com.softdata.dyhxx.helper.util.loge
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class SelectLanguageFragment :
    BaseFragment<FragmentSelectLanguageBinding>(FragmentSelectLanguageBinding::inflate) {

    private var pref: SharedPreferences? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.idButtonUz.setOnClickListener(this::onSelectLanguage)
        binding.idButtonRu.setOnClickListener(this::onSelectLanguage)
        binding.idButtonEn.setOnClickListener(this::onSelectLanguage)

        pref = getPref(requireActivity())
        loadPref()
    }


    private fun loadPref() {
        val lang = pref?.getString(PREF_LANG_KEY, "eng")
        loge(lang)
        setLocate(lang!!)
    }

    private fun setLocate(lang: String) {
        val locale = Locale(lang)
        val config = resources.configuration
        Locale.setDefault(locale)
        config.locale = locale
        resources.updateConfiguration(config, requireContext().resources.displayMetrics)

        val editor = pref?.edit()
        editor?.putString(PREF_LANG_KEY, lang)
        editor?.apply()
    }

    private fun onSelectLanguage(v: View) {
        when (v.id) {
            R.id.id_button_uz -> setLocate("uz")
            R.id.id_button_ru -> setLocate("ru")
            R.id.id_button_en -> setLocate("en")
        }

        getBaseActivity {
            it.navController?.navigate(R.id.action_selectLanguageFragment_to_authFragment)
        }
    }
    
}