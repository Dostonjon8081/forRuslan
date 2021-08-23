package com.softdata.dyhxx.core_fragment.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.softdata.dyhxx.R
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.databinding.FragmentHomeBinding
import com.softdata.dyhxx.helper.db.CarEntity
import com.softdata.dyhxx.helper.db.carViewModel.CarViewModel
import com.softdata.dyhxx.helper.network.viewModel.ApiViewModel
import com.softdata.dyhxx.helper.util.isOnline
import com.softdata.dyhxx.helper.util.logd
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CarRvAdapter

    private val carViewModel: CarViewModel by activityViewModels()
    private val apiViewModel: ApiViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        carViewModel.allCars()
    }

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
                    binding.idBottomNavigation.visibility = View.VISIBLE
                }, 400)
            }
        }

        binding.homeFragmentButtonAddCar.setOnClickListener { addCar() }

        carViewModel.allCar.observe(viewLifecycleOwner, object : Observer<List<CarEntity>> {
            override fun onChanged(t: List<CarEntity>?) {
                if (t?.size!! > 0) {
                    adapter = CarRvAdapter(t)
                    binding.homeFragmentDescription.visibility = View.GONE
                    binding.homeFragmentRv.visibility = View.VISIBLE
                    binding.homeFragmentRv.adapter = adapter
                    binding.homeFragmentButtonAddCar.visibility = if (t.size >= 8) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
                } else {
                    binding.homeFragmentDescription.visibility = View.VISIBLE
                    binding.homeFragmentRv.visibility = View.GONE
                }
                Log.d("holt", "onViewCreated: $t")
            }
        })
    }

    private fun addCar() {
        (activity as MainActivity).navController?.navigate(R.id.action_home_fragment_to_addCarFragment)
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}