package com.softdata.dyhxx.core_fragment.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.softdata.dyhxx.R
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.databinding.FragmetnAccountLanguageBinding

class AccountLanguageFragment :
    BottomSheetDialogFragment() {

    private var _binding: FragmetnAccountLanguageBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmetnAccountLanguageBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.uzbek.setOnClickListener(this::selectLanguage)
        binding!!.rus.setOnClickListener(this::selectLanguage)
        binding!!.english.setOnClickListener(this::selectLanguage)
    }

    private fun selectLanguage(view: View) {
        (requireActivity() as MainActivity).apply {
            when (view.id) {
                R.id.rus -> this.setLocale("ru")
                R.id.english -> this.setLocale("en")
                R.id.uzbek -> this.setLocale("uz")
            }
            navController!!.navigate(R.id.account_fragment)
        }

//        when (view.id) {
//            R.id.rus -> LocaleHelper.setLocale(requireActivity(),"ru")
//            R.id.english -> LocaleHelper.setLocale(requireActivity(),"en")
//            R.id.uzbek ->  LocaleHelper.setLocale(requireActivity(),"uz")}
        this.dismiss()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }

}