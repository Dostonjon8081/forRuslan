package uz.fizmasoft.dyhxx.core_fragment.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.activity.MainActivity
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentHomeBinding
import uz.fizmasoft.dyhxx.helper.db.CarEntity
import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.network.model.AllCars
import uz.fizmasoft.dyhxx.helper.network.model.RemoveCarModel
import uz.fizmasoft.dyhxx.helper.util.*
import java.util.concurrent.Executors


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), RvItemClick {

    private val viewModel: HomeViewModel by activityViewModels()
    private var listCarEntity = mutableListOf<CarEntity>()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { CarRvAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.allCarsDB()

        Handler(Looper.getMainLooper()).postDelayed({
            (activity as? MainActivity)?.let {
                if (!it.binding.idBottomNavigation.isVisible) {
                    it.binding.idBottomNavigation.visibility = View.VISIBLE
                }
            }
        }, 400)

        binding.homeFragmentButtonAddCar.setOnClickListener { addCar() }
        binding.homeFragmentButtonAddCarBtn.setOnClickListener { addCar() }
        binding.homeFragmentSwipeRefresh.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(),
                R.color.toolbar_color
            )
        )
        binding.homeFragmentSwipeRefresh.setOnRefreshListener(this::swipeRefresh)

        loadData()
    }

    private fun loadData() {
        viewModel.allCarDB.observe(viewLifecycleOwner, { t ->
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
                //                    logd(t)
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
        })
    }

    private fun swipeRefresh() {

        if (requireActivity().intent.data == null) {
            viewModel.allCarsApi(
                AllCars(
                    getPref(requireActivity()).getString(
                        PREF_USER_ID_KEY,
                        ""
                    )!!
                )
            )
        }
        viewModel.responseAllCarsApi.observe(this, EventObserver { result ->

            when (result) {
                is NetworkResult.Loading -> {
                }
                is NetworkResult.Success -> {
                    binding.homeFragmentSwipeRefresh.isRefreshing = false
                    Executors.newSingleThreadExecutor().execute {
                        if (result.data?.status == 200) {
                            for (resultItem in result.data.data) {

                                if (listCarEntity.all { it.carNumber != resultItem.passport }) {

                                    viewModel.insertCarDB(
                                        CarEntity(
                                            0,
                                            resultItem.passport,
                                            resultItem.tex_passport
                                        )
                                    )
                                }
                            }

                            if (result.data.data.size < listCarEntity.size) {
                                for (listItem in listCarEntity) {
                                    if (result.data.data.all { it.passport != listItem.carNumber }) {
                                        viewModel.removeCarDB(listItem.carNumber)
                                    }
                                }
                            }
                        }
                    }
                }
                is NetworkResult.Error -> {
                }
            }
            navigateToHome()
        })
        binding.homeFragmentSwipeRefresh.isRefreshing = false
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
        (activity as? MainActivity)?.let {
            it.navController?.navigate(
                HomeFragmentDirections.actionHomeFragmentToViolationFragment(
                    listCarEntity[position]
                )
            )
        }
    }

    private fun deleteCar(carNumber: String) {
        val removeCarModel = RemoveCarModel(
            getPref(requireActivity()).getString(PREF_USER_ID_KEY, "")!!,
            carNumber
        )
        viewModel.removeCarApi(removeCarModel)
        viewModel.responseRemoveCarApi.observe(viewLifecycleOwner, EventObserver {
//            if (it.data != null) {
            when (it) {
                is NetworkResult.Loading -> {
                }
                is NetworkResult.Success -> {
                    viewModel.removeCarDB(carNumber)
                }
                is NetworkResult.Error -> {
                }
//                }
            }
            navigateToHome()
        })

    }

    private fun navigateToHome() {
        getBaseActivity {
            it.navController!!.navigate(R.id.home_fragment)
        }
    }

}
