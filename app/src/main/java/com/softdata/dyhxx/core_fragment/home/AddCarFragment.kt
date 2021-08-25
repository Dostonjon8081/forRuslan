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
import androidx.navigation.fragment.navArgs
import com.softdata.dyhxx.R
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.databinding.FragmentAddCarBinding
import com.softdata.dyhxx.helper.db.CarEntity
import com.softdata.dyhxx.helper.db.carViewModel.CarViewModel
import com.softdata.dyhxx.helper.network.model.SaveCarModel
import com.softdata.dyhxx.helper.network.viewModel.ApiViewModel
import com.softdata.dyhxx.helper.util.PREF_USER_ID_KEY
import com.softdata.dyhxx.helper.util.carToast
import com.softdata.dyhxx.helper.util.getPref
import com.softdata.dyhxx.helper.util.isOnline
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCarFragment : Fragment(), SpinnerItemClick {

    private val viewModel: CarViewModel by activityViewModels()
    private val apiViewModel: ApiViewModel by activityViewModels()

    private val args: AddCarFragmentArgs by navArgs()

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addCarFragmentToolBar.title = getString(R.string.add_car)

        setupSpinner()
        setup()

        binding.apply {
            addCarFragmentButtonCancel.setOnClickListener { clickButton(binding.addCarFragmentButtonCancel.id) }
            addCarFragmentButtonSave.setOnClickListener { clickButton(binding.addCarFragmentButtonSave.id) }
            addCarFragmentArrowBack.setOnClickListener { clickButton(binding.addCarFragmentArrowBack.id) }
        }
    }

    private fun setup() {
        val carArgs = args.carArgs
        if (carArgs != null) {
            with(binding) {
                addCarFragmentEtCarNumber.setText(carArgs.carNumber)
                addCarFragmentTexPassSeries.setText(carArgs.texPass.substring(0,3))
                addCarFragmentTexPassNumber.setText(carArgs.texPass.substring(3))
            }
        }
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
            R.id.add_car_fragment_button_save -> saveCar()
            R.id.add_car_fragment_arrow_back -> (activity as MainActivity).onBackPressed()
        }
    }

    private fun saveCar() {

        val carNumber = binding.addCarFragmentEtCarNumber.text.toString().uppercase()
        val carTexPasSeries = binding.addCarFragmentTexPassSeries.text.toString().uppercase()
        val carTexPasNumber = binding.addCarFragmentTexPassNumber.text.toString()

        if (carNumber.length >= 8
            && carTexPasNumber.length == 7
            && carTexPasSeries.length >= 3
            && carMark.isNotEmpty()
            && carModel.isNotEmpty()
        ) {
            val carEntity =
                CarEntity(
                    0,
                    carNumber,
                    carTexPasSeries +
                            carTexPasNumber,
                    carMark,
                    carModel
                )

            if (isOnline(requireContext())) {


                apiViewModel.saveCar(
                    SaveCarModel(
                        getPref(requireActivity()).getString(PREF_USER_ID_KEY, "")!!, carNumber,
                        carTexPasSeries + carTexPasNumber
                    )
                )

                apiViewModel.responseSaveCar.observe(viewLifecycleOwner) {
                    when (it.data?.status) {
                        200 -> {
                            viewModel.insertCar(carEntity)
                            (activity as MainActivity).onBackPressed()
                        }
                        401 -> carToast(requireContext(), getString(R.string.car_exist))
                        400 -> carToast(requireContext(), getString(R.string.bad_request))
                    }
                }


            } else {
                carToast(requireContext(), getString(R.string.not_ethernet))
            }
        } else {
            carToast(requireContext(), getString(R.string.wrong))
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


    override fun onDetach() {
        super.onDetach()
        _binding = null
    }

}


