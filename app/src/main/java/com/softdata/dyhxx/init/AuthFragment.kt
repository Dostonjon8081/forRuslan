package com.softdata.dyhxx.init

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.softdata.dyhxx.R
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.databinding.FragmentAuthBinding
import com.softdata.dyhxx.util.FIRST_INIT
import com.softdata.dyhxx.util.getPref

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
            val edit = pref!!.edit()
            edit.putBoolean(FIRST_INIT,false).apply()

            (activity as MainActivity).navController?.navigate(R.id.action_authFragment_to_homeFragment)
        }
    }
}