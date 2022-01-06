package uz.fizmasoft.dyhxx.core_fragment.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentHomeBinding
import uz.fizmasoft.dyhxx.helper.db.CarEntity
import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.util.*
import uz.fizmasoft.dyhxx.main_fragment.MainFragmentDirections
import java.util.concurrent.Executors


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), RvItemClick {

    private val viewModel: HomeViewModel by activityViewModels()
    private var listCarEntity = mutableListOf<CarEntity>()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { CarRvAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.allCarsDB()

        binding.homeFragmentSwipeRefresh.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(),
                R.color.button_blue_color
            )
        )

        binding.homeFragmentSwipeRefresh.setOnRefreshListener(this::swipeRefresh)

        loadData()
    }

    private fun loadData() {
        viewModel.allCarDB.observe(viewLifecycleOwner, { t ->
            logd(t)
            if (t?.size!! > 0) {
//                binding.homeFragmentButtonAddCar.visibility = View.VISIBLE
                /* if (t.size >= 8) {
 //                    binding.homeFragmentButtonAddCar.visibility = View.GONE
                 } else {
 //                    binding.homeFragmentButtonAddCar.visibility = View.VISIBLE
                 }*/
                adapter.rvClickListener(this@HomeFragment)
                listCarEntity.clear()
                listCarEntity.addAll(t)

                binding.homeFragmentRv.visibility = View.VISIBLE
                binding.homeFragmentNoCarContainer.visibility = View.GONE
                binding.homeFragmentRv.adapter = adapter
                adapter.submitList(t)
//                binding.homeFragmentButtonAddCar.visibility = if (t.size >= 8) {
//                    View.GONE
//                } else {
//                    View.VISIBLE
//                }
            } else {
                swipeRefresh()
                binding.homeFragmentNoCarContainer.visibility = View.VISIBLE
//                binding.homeFragmentButtonAddCar.visibility = View.GONE
                binding.homeFragmentRv.visibility = View.GONE
            }
        })
    }

    private fun swipeRefresh() {

        if (requireActivity().intent.data == null) {
            viewModel.allCarsApi(
                getPref(requireActivity()).getString(
                    PREF_TOKEN_KEY,
                    ""
                )!!
            )
        }
        viewModel.responseAllCarsApi.observe(this, EventObserver { result ->

            when (result) {
                is NetworkResult.Loading -> {
                }

                is NetworkResult.Success -> {
                    binding.homeFragmentSwipeRefresh.isRefreshing = false
                    Executors.newSingleThreadExecutor().execute {
                        if (result.data?.isNotEmpty()!!) {
                            for (resultItem in result.data) {

                                if (listCarEntity.all { it.carNumber != resultItem.car_number }) {

                                    viewModel.insertCarDB(
                                        CarEntity(
                                            0,
                                            resultItem.car_number,
                                            resultItem.tex_passport
                                        )
                                    )
                                }
                            }

                            if (result.data.size < listCarEntity.size) {
                                for (listItem in listCarEntity) {
                                    if (result.data.all { it.car_number != listItem.carNumber }) {
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
        })
        binding.homeFragmentSwipeRefresh.isRefreshing = false
    }



    override fun clickedItem(carEntity: CarEntity) {
        getBaseActivity {
            it.navController?.navigate(MainFragmentDirections.actionMainFragmentToViolationFragment())
        }
    }

    override fun clickedItemEdit(carEntity: CarEntity) {
        getBaseActivity {
            it.navController?.navigate(
                MainFragmentDirections.actionMainFragmentToAddCarFragment(carEntity)
            )
        }
    }

    /*    private fun addCar() {
            getBaseActivity {
    //            it.navController?.navigate(HomeFragmentDirections.actionHomeFragmentToAddCarFragment())
            }
        }*/

/* fun clickedItemDelete(carNumber: String) {
     val builder = AlertDialog.Builder(requireContext())
         .setMessage(R.string.delete_dialog_title)
         .setPositiveButton(R.string.yes) { _, _ ->
             deleteCar(carNumber)
         }
         .setNegativeButton(R.string.no) { _, _ ->
         }
     builder.create()
     builder.show()

 }*/

    /*
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
    //            navigateToHome()
            })

        }*/

    /*  private fun navigateToHome() {
          getBaseActivity {
              it.navController!!.navigate(R.id.home_fragment)
          }
      }*/

}
