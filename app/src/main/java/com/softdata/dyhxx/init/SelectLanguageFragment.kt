package com.softdata.dyhxx.init

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.softdata.dyhxx.R
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.databinding.FragmentSelectLanguageBinding
import com.softdata.dyhxx.helper.util.getPref
import com.softdata.dyhxx.helper.util.loge
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class SelectLanguageFragment : Fragment() {


    private var _binding: FragmentSelectLanguageBinding? = null
    private val binding get() = _binding!!
    private var pref: SharedPreferences? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.idButtonUz.setOnClickListener(this::onSelectLanguage)
        binding.idButtonRu.setOnClickListener(this::onSelectLanguage)
        binding.idButtonEn.setOnClickListener(this::onSelectLanguage)

        pref = getPref(requireActivity())
        loadPref()


    }


    private fun loadPref() {
//        pref?.edit()?.putString("key", "uz")?.apply()
        val lang = pref?.getString("key", "eng")
        loge(lang)
        setLocate(lang!!)
    }

    private fun setLocate(lang: String) {
        Log.d("selected", "setLocate: $lang")
        Log.d("selected", resources.getString(R.string.look))
        val locale = Locale(lang)
        val config = resources.configuration
        Locale.setDefault(locale)
        config.locale = locale
        resources.updateConfiguration(config, requireContext().resources.displayMetrics)

        val editor = pref?.edit()
        editor?.putString("key", lang)
        editor?.apply()

        Log.d("selected", resources.getString(R.string.look))
    }

    private fun onSelectLanguage(v: View) {
        when (v.id) {
            R.id.id_button_uz -> setLocate("uz")
            R.id.id_button_ru -> setLocate("ru")
            R.id.id_button_en -> setLocate("en")
        }

        (activity as MainActivity).navController?.navigate(R.id.action_selectLanguageFragment_to_authFragment)
    }

}