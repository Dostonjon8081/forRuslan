package com.softdata.dyhxx.core_fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.softdata.dyhxx.R
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.databinding.FragmentHomeBinding
import com.softdata.dyhxx.helper.db.CarEntity
import com.softdata.dyhxx.helper.db.carViewModel.CarViewModel
import com.softdata.dyhxx.helper.network.model.RemoveCarModel
import com.softdata.dyhxx.helper.network.viewModel.ApiViewModel
import com.softdata.dyhxx.helper.util.PREF_USER_ID_KEY
import com.softdata.dyhxx.helper.util.getPref
import com.softdata.dyhxx.helper.util.loge
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), RvItemClick {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CarRvAdapter

    private val carViewModel: CarViewModel by activityViewModels()
    private val apiViewModel: ApiViewModel by activityViewModels()
    private var listCarEntity = listOf<CarEntity>()

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
                    listCarEntity = t
                    adapter = CarRvAdapter(t, this@HomeFragment)
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
        (activity as MainActivity).navController?.navigate(HomeFragmentDirections.actionHomeFragmentToAddCarFragment())
    }


    @SuppressLint("ResourceType")
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
        (activity as MainActivity).navController?.navigate(HomeFragmentDirections.actionHomeFragmentToViolationFragment(listCarEntity[position]))
    }

    private fun deleteCar(position: Int) {
        val removeCarModel = RemoveCarModel(getPref(requireActivity()).getString(PREF_USER_ID_KEY,"")!!,listCarEntity[position].carNumber)
        loge(listCarEntity[position].toString())
        apiViewModel.removeCar(removeCarModel)
        apiViewModel.responseRemoveCar.observe(viewLifecycleOwner){
            if (it.data!=null && it.data.status == 200){
                carViewModel.removeCar(position+1)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}
