package com.softdata.dyhxx.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.softdata.dyhxx.R
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).apply {
            if (!this.binding.idBottomNavigation.isVisible) {
                Handler().postDelayed({
//                    binding.idBottomNavigation.animation.duration = 400
                    binding.idBottomNavigation.visibility = View.VISIBLE
                }, 400)
            }
        }

        binding.idHomeFragmentButtonAddCar.setOnClickListener { addCar() }


    }

    private fun addCar() {
        (activity as MainActivity).navController?.navigate(R.id.action_home_fragment_to_addCarFragment)
    }
}