package com.softdata.dyhxx.violation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.databinding.FragmentViolationBinding

class ViolationFragment : Fragment() {

    private var _binding: FragmentViolationBinding? = null
    private val binding get() =  _binding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as MainActivity).navController?.popBackStack()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViolationBinding.inflate(inflater,container,false)
        return binding!!.root
    }


}