package com.softdata.dyhxx.core_fragment.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.softdata.dyhxx.R
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.base.BaseFragment
import com.softdata.dyhxx.databinding.FragmentHomeBinding
import com.softdata.dyhxx.helper.db.CarEntity
import com.softdata.dyhxx.helper.network.model.RemoveCarModel
import com.softdata.dyhxx.helper.util.PREF_USER_ID_KEY
import com.softdata.dyhxx.helper.util.getPref
import com.softdata.dyhxx.helper.util.logd
import com.softdata.dyhxx.helper.util.loge
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate), RvItemClick {

//    private lateinit var adapter: CarRvAdapter

    private val viewModel: HomeViewModel by activityViewModels()
    private var listCarEntity = listOf<CarEntity>()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { CarRvAdapter() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.allCarsDB()
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

        viewModel.allCarDB.observe(viewLifecycleOwner, object : Observer<MutableList<CarEntity>> {
            override fun onChanged(t: MutableList<CarEntity>?) {
                if (t?.size!! > 0) {
                    logd(t)
                    adapter.rvClickListener(this@HomeFragment)
                    listCarEntity = t
//                    adapter = CarRvAdapter( t,this@HomeFragment)
                    binding.homeFragmentDescription.visibility = View.GONE
                    binding.homeFragmentRv.visibility = View.VISIBLE
                    binding.homeFragmentRv.adapter = adapter
                    adapter.submitList(t)
                    binding.homeFragmentButtonAddCar.visibility = if (t.size >= 8) {
                        View.GONE
                    } else {
                        View.VISIBLE
                    }
                } else {
                    binding.homeFragmentDescription.visibility = View.VISIBLE
                    binding.homeFragmentRv.visibility = View.GONE
                }
            }
        })
    }

    private fun addCar() {
        (activity as MainActivity).navController?.navigate(HomeFragmentDirections.actionHomeFragmentToAddCarFragment())
    }

    override fun clickedItemDelete(position: Int) {
//        (activity as MainActivity).navController?.navigate(HomeFragmentDirections.actionHomeFragmentToAddCarFragment(carEntity))

        val builder = AlertDialog.Builder(requireContext())
            .setMessage(R.string.delete_dialog_title)
            .setPositiveButton(R.string.yes) { _, _ ->
                deleteCar(position)
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

    private fun deleteCar(position: Int) {
        val removeCarModel = RemoveCarModel(
            getPref(requireActivity()).getString(PREF_USER_ID_KEY, "")!!,
            listCarEntity[position].carNumber
        )

        loge(listCarEntity[position].toString())
        viewModel.removeCarApi(removeCarModel)
        viewModel.responseRemoveCarApi.observe(viewLifecycleOwner) {
            if (it.data != null) {
                viewModel.removeCarDB(listCarEntity[position].carNumber)
            }
        }
    }

}
