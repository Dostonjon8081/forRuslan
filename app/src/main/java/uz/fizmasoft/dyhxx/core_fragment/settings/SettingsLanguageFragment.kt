package uz.fizmasoft.dyhxx.core_fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.activity.MainActivity
import uz.fizmasoft.dyhxx.databinding.FragmetnSettingsLanguageBinding

class SettingsLanguageFragment :
    BottomSheetDialogFragment() {

    private var _binding: FragmetnSettingsLanguageBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmetnSettingsLanguageBinding.inflate(layoutInflater, container, false)
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

            this.bottomNavController!!.navigate(R.id.setting)

            val bottomView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
            bottomView?.menu?.get(0)?.title = resources.getString(R.string.home)
            bottomView?.menu?.get(2)?.title = resources.getString(R.string.settings)

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