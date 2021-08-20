package com.softdata.dyhxx.init

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.softdata.dyhxx.databinding.FragmentAuthBinding
import com.softdata.dyhxx.helper.util.getPref
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthFragment : Fragment() {

    private var pref: SharedPreferences? = null
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = getPref(requireActivity())
        binding.idAuthFragmentButtonTelegram.setOnClickListener {


            val myIntent = Intent(Intent.ACTION_VIEW,Uri.parse("https://t.me/dyhxxuz_auth_bot"))
//            startActivityForResult(myIntent,1001)
            startActivity(myIntent)
//            val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){
//
//            }
//            getContent.launch("https://t.me/dyhxxuz_auth_bot")

//            requireActivity().startActivity(Intent.createChooser(myIntent, "Share with"))
//            val edit = pref!!.edit()
//            edit.putBoolean(FIRST_INIT,false).apply()

//            (activity as MainActivity).navController?.navigate(R.id.action_authFragment_to_homeFragment)
        }
    }

    private fun intentMessageTelegram() {
        val appName = "org.telegram.messenger"
        val isAppInstalled: Boolean = isAppAvailable( appName)
        if (isAppInstalled) {
            val myIntent = Intent(Intent.ACTION_VIEW,Uri.parse("https://t.me/dyhxxuz_auth_bot"))
            myIntent.setPackage(appName)//
            requireActivity().startActivity(Intent.createChooser(myIntent, "Share with"))
        } else {
            Toast.makeText(requireActivity(), "Telegram not Installed", Toast.LENGTH_SHORT).show()
        }
    }
    private fun isAppAvailable(appName: String?): Boolean {
        val pm: PackageManager = requireContext().packageManager
        return try {
            pm.getPackageInfo(appName!!, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}