package com.softdata.dyhxx.core_fragment.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.softdata.dyhxx.R
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.base.BaseFragment
import com.softdata.dyhxx.databinding.FragmentHomeBinding
import com.softdata.dyhxx.helper.db.CarEntity
import com.softdata.dyhxx.helper.network.NetworkResult
import com.softdata.dyhxx.helper.network.model.AllCars
import com.softdata.dyhxx.helper.network.model.RemoveCarModel
import com.softdata.dyhxx.helper.util.*
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executors


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), RvItemClick {

    private val viewModel: HomeViewModel by activityViewModels()
    private var listCarEntity = mutableListOf<CarEntity>()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { CarRvAdapter() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.allCarsDB()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
        (activity as MainActivity).apply {
            if (!this.binding.idBottomNavigation.isVisible) {

                    binding.idBottomNavigation.visibility = View.VISIBLE
                }
            }
        }, 400)

        binding.homeFragmentButtonAddCar.setOnClickListener { addCar() }
        binding.homeFragmentButtonAddCarBtn.setOnClickListener { addCar() }
        binding.homeFragmentSwipeRefresh.setColorSchemeColors(ContextCompat.getColor(requireContext(),R.color.toolbar_color))
        binding.homeFragmentSwipeRefresh.setOnRefreshListener(this::swipeRefresh)

        loadData()
    }

    private fun loadData() {
        viewModel.allCarDB.observe(viewLifecycleOwner, object : Observer<MutableList<CarEntity>> {
            override fun onChanged(t: MutableList<CarEntity>?) {
                if (t?.size!! > 0) {
                    binding.homeFragmentButtonAddCar.visibility = View.VISIBLE
                    if (t.size >= 8) {
                        binding.homeFragmentButtonAddCar.visibility = View.GONE
                    } else {
                        binding.homeFragmentButtonAddCar.visibility = View.VISIBLE
                    }
                    adapter.rvClickListener(this@HomeFragment)
                    listCarEntity.clear()
                    listCarEntity.addAll(t)
                    logd(t)
                    binding.homeFragmentRv.visibility = View.VISIBLE
                    binding.homeFragmentNoCarContainer.visibility = View.GONE
                    binding.homeFragmentRv.adapter = adapter
                    adapter.submitList(t)
                    binding.homeFragmentButtonAddCar.visibility = if (t.size >= 8) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
                } else {
                    binding.homeFragmentNoCarContainer.visibility = View.VISIBLE
                    binding.homeFragmentButtonAddCar.visibility = View.GONE
                    binding.homeFragmentRv.visibility = View.GONE
                }
            }
        })
    }

    private fun swipeRefresh() {
        logd("in swipe")
        viewModel.allCarsApi(AllCars(getPref(requireActivity()).getString(PREF_USER_ID_KEY, "")!!))
        viewModel.responseAllCarsApi.observe(this, EventObserver { result ->
            logd(result)
            when (result) {
                is NetworkResult.Loading -> {
                    logd("in loading")
                }
                is NetworkResult.Success -> {
                    binding.homeFragmentSwipeRefresh.isRefreshing = false
                    logd("in success")
                    Executors.newSingleThreadExecutor().execute {
                        if (result.data?.status == 200)
                            for (resultItem in result.data.data) {
                                logd("in for 1")
                                if (listCarEntity.all { it.carNumber != resultItem.passport }) {
                                    logd("in if")
                                    viewModel.insertCarDB(
                                        CarEntity(
                                            0,
                                            resultItem.passport,
                                            resultItem.tex_passport
                                        )
                                    )
                                }
                            }
                    }
                }
                is NetworkResult.Error -> {
                    logd("in error")
                }
            }
            navigateToHome()
        })
    }

    private fun addCar() {
        getBaseActivity {
            it.navController?.navigate(HomeFragmentDirections.actionHomeFragmentToAddCarFragment())
        }
    }

    override fun clickedItemDelete(carNumber: String) {
        val builder = AlertDialog.Builder(requireContext())
            .setMessage(R.string.delete_dialog_title)
            .setPositiveButton(R.string.yes) { _, _ ->
                deleteCar(carNumber)
            }
            .setNegativeButton(R.string.no) { _, _ ->
            }
        builder.create()
        builder.show()

    }

    override fun clickedItem(position: Int) {
        (activity as MainActivity).navController?.navigate(
            HomeFragmentDirections.actionHomeFragmentToViolationFragment(
                listCarEntity[position]
            )
        )
    }

    private fun deleteCar(carNumber: String) {
        val removeCarModel = RemoveCarModel(
            getPref(requireActivity()).getString(PREF_USER_ID_KEY, "")!!,
            carNumber
        )

        loge(carNumber)
        viewModel.removeCarApi(removeCarModel)
        viewModel.responseRemoveCarApi.observe(viewLifecycleOwner, EventObserver {
//            if (it.data != null) {
            when (it) {
                is NetworkResult.Loading -> {
                }
                is NetworkResult.Success -> {
                    viewModel.removeCarDB(carNumber)
                    navigateToHome()
                }
                is NetworkResult.Error -> {
                }
//                }
            }

        })
    }

    private fun navigateToHome() {
        getBaseActivity {
            it.navController!!.navigate(R.id.home_fragment)
        }
    }

}
