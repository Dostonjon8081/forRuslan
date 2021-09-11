package com.softdata.dyhxx.violation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.base.BaseFragment
import com.softdata.dyhxx.databinding.FragmentViolationBinding
import com.softdata.dyhxx.helper.db.CarEntity
import com.softdata.dyhxx.helper.network.NetworkResult
import com.softdata.dyhxx.helper.util.EventObserver
import com.softdata.dyhxx.helper.util.PREF_USER_ID_KEY
import com.softdata.dyhxx.helper.util.getPref

class ViolationFragment :
    BaseFragment<FragmentViolationBinding>(FragmentViolationBinding::inflate) {

    private val viewModel: ViolationViewModel by activityViewModels()
    private val args: ViolationFragmentArgs by navArgs()
    private var arg: CarEntity? = null
    private val violationRvAdapter by lazy(LazyThreadSafetyMode.NONE) { ViolationRvAdapter() }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as MainActivity).navController?.popBackStack()
            }
        })

        arg = args.violationArgs

        viewModel.getViolationApi(
            ViolationCarApiModel(
                getPref(requireActivity())
                    .getString(PREF_USER_ID_KEY, "")!!,
                arg!!.carNumber,
                arg!!.texPass
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()

    }

    private fun loadData() {

        binding.violationFragmentTitle.text = arg!!.carNumber.substring(0,2) + " "+ arg!!.carNumber.substring(2)
        viewModel.responseViolationApiApi.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is NetworkResult.Success -> {
                    if (it.data?.status == 200) {
                        initDataToRv(it.data.data)
                    }
                }
                is NetworkResult.Error -> {
                }
                is NetworkResult.Loading -> {
                }
            }
        })
    }

    private fun initDataToRv(data: List<ViolationCarApiModelResponseData>) {

        val list: List<ViolationCarModel> = data.map {
            ViolationCarModel(
                it.id,
                it.drb, it.qarorSery, it.qarorNumber,
                it.violationTime, it.violationType,
                it.sum, it.location
            )
        }
        violationRvAdapter.submitList(list)

        binding.violationFragmentRv.visibility = View.VISIBLE
        binding.violationFragmentRv.layoutManager = LinearLayoutManager(requireContext())
        binding.violationFragmentRv.adapter = violationRvAdapter

    }

}