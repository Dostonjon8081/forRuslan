package com.softdata.dyhxx.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.softdata.dyhxx.R
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.databinding.FragmentAddCarBinding

class AddCarFragment : Fragment(), AdapterView.OnItemClickListener {

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

        val arrayAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, carMarks)

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.addCarFragmentSpinnerCarMarks.adapter = arrayAdapter
        binding.addCarFragmentSpinnerCarModels.adapter = arrayAdapter

        binding.addCarFragmentButtonCancel.setOnClickListener { clickButton(binding.addCarFragmentButtonCancel.id) }

    }

    private fun clickButton(id: Int) {
        if (id == R.id.add_car_fragment_button_cancel) {
            (activity as MainActivity).onBackPressed()
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }
}


