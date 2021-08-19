package com.softdata.dyhxx.core_fragment.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.softdata.dyhxx.R
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.databinding.FragmentAddCarBinding
import com.softdata.dyhxx.helper.util.carToast
import com.softdata.dyhxx.helper.util.db.CarEntity
import com.softdata.dyhxx.helper.util.db.carViewModel.CarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCarFragment : Fragment(), SpinnerItemClick {

    private val viewModel: CarViewModel by activityViewModels()
    private val carMarks = mutableListOf(
        "Select",
        "Daewoo",
        "Chevrolet",
        "Abarth",
        "Acura",
        "Alfa Romeo",
        "Aston Martin",
        "Audi",
        "Bentley",
        "BMW", "Buick"
    )

    private var _binding: FragmentAddCarBinding? = null
    private val binding get() = _binding!!

    private var carMark = ""
    private var carModel = ""

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
    ): View? {
        _binding = FragmentAddCarBinding.inflate(inflater, container, false)
        return binding.root

//        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addCarFragmentToolBar.title = getString(R.string.add_car)

        setupSpinner()

        binding.addCarFragmentButtonCancel.setOnClickListener { clickButton(binding.addCarFragmentButtonCancel.id) }
        binding.addCarFragmentButtonSave.setOnClickListener { clickButton(binding.addCarFragmentButtonSave.id) }
        binding.addCarFragmentArrowBack.setOnClickListener { clickButton(binding.addCarFragmentArrowBack.id) }
    }

    private fun setupSpinner() {
        val spinnerAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, carMarks)

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.addCarFragmentSpinnerCarMarks.adapter = spinnerAdapter
        binding.addCarFragmentSpinnerCarModels.adapter = spinnerAdapter
        binding.addCarFragmentSpinnerCarMarks.onItemSelectedListener = this
        binding.addCarFragmentSpinnerCarModels.onItemSelectedListener = this

    }

    private fun clickButton(id: Int) {
        when (id) {
            R.id.add_car_fragment_button_cancel -> (activity as MainActivity).onBackPressed()
            R.id.add_car_fragment_button_save -> writeToDB()
            R.id.add_car_fragment_arrow_back -> (activity as MainActivity).onBackPressed()
        }
    }

    private fun writeToDB() {

        val carNumber = binding.addCarFragmentEtCarNumber.text.toString()
        val carTexPasSeries = binding.addCarFragmentTexPassSeries.text.toString()
        val carTexPasNumber = binding.addCarFragmentTexPassNumber.text.toString()

        if (carNumber.isNotEmpty()
            && carTexPasNumber.isNotEmpty()
            && carTexPasSeries.isNotEmpty()
            && carMark.isNotEmpty()
            && carModel.isNotEmpty()
        ) {
            val carEntity =
                CarEntity(
                    0,
                    carNumber,
                    carTexPasSeries,
                    carTexPasNumber,
                    carMark,
                    carModel
                )

            viewModel.insertCar(carEntity)
            (activity as MainActivity).onBackPressed()
        } else {
            carToast(requireContext(), "wrong something")
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d("parent", "onItemSelected: ${parent?.id}")
        if (parent?.id == R.id.add_car_fragment_spinner_car_marks) {
            carMark = carMarks[position]
        } else {
            carModel = carMarks[position]
        }
    }

}


