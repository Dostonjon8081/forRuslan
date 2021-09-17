package com.softdata.dyhxx.core_fragment.home.add_car

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.softdata.dyhxx.R
import com.softdata.dyhxx.activity.MainActivity
import com.softdata.dyhxx.base.BaseFragment
import com.softdata.dyhxx.core_fragment.home.CarRvAdapter
import com.softdata.dyhxx.core_fragment.home.SpinnerItemClick
import com.softdata.dyhxx.databinding.FragmentAddCarBinding
import com.softdata.dyhxx.helper.db.CarEntity
import com.softdata.dyhxx.helper.network.model.SaveCarModel
import com.softdata.dyhxx.helper.util.PREF_USER_ID_KEY
import com.softdata.dyhxx.helper.util.carToast
import com.softdata.dyhxx.helper.util.getPref
import com.softdata.dyhxx.helper.util.isOnline
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCarFragment : BaseFragment<FragmentAddCarBinding>(FragmentAddCarBinding::inflate),
    SpinnerItemClick {

    private val viewModel: AddCarViewModel by activityViewModels()

    private val args: AddCarFragmentArgs by navArgs()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) { CarRvAdapter() }

    private var carMark = ""
    private var carModel = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                getBaseActivity {
                    it.navController?.popBackStack()
                }
            }
        })
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
                addCarFragmentTexPassSeries.setText(carArgs.texPass.substring(0, 3))
                addCarFragmentTexPassNumber.setText(carArgs.texPass.substring(3))
            }
        }
    }

    private fun setupSpinner() {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.car_marks).sortedBy { it }).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.addCarFragmentSpinnerCarMarks.adapter = adapter
            binding.addCarFragmentSpinnerCarMarks.onItemSelectedListener = this
        }
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

        if (!binding.addCarFragmentEditTextCarModels.text.isNullOrEmpty()) {
            carModel = binding.addCarFragmentEditTextCarModels.text.toString()
        }

        if (carNumber.length >= 8
            && carTexPasNumber.length == 7
            && carTexPasSeries.length >= 3
            && carMark.isNotEmpty()
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


                viewModel.saveCarApi(
                    SaveCarModel(
                        getPref(requireActivity()).getString(PREF_USER_ID_KEY, "")!!, carNumber,
                        carTexPasSeries + carTexPasNumber
                    )
                )

                viewModel.responseSaveCarApi.observe(viewLifecycleOwner) {
                    when (it.data?.status) {
                        200 -> {
                            viewModel.insertCarDB(carEntity)
                            (activity as MainActivity).onBackPressed()
                            adapter.addCar(carEntity)
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
        if (parent?.id == R.id.add_car_fragment_spinner_car_marks) {

            carMark = binding.addCarFragmentSpinnerCarMarks.selectedItem.toString()
            if (carMark.isNotEmpty()) {
                setupModelSpinner()
            }
        } else {
            carMark = binding.addCarFragmentSpinnerCarModels.selectedItem.toString()
        }
    }

    private fun setupModelSpinner() {
        var arrayRes: Int? = R.array.Chevrolet
        when (carMark) {
            "Daewoo" -> {
                arrayRes = R.array.Daewoo
            }
            "Chevrolet" -> {
                arrayRes = R.array.Chevrolet
            }
            "Audi" -> {
                arrayRes = R.array.Audi
            }
            "Baic" -> {
                arrayRes = R.array.Baic
            }
            "BMW" -> {
                R.array.BMW
            }
            "Ford" -> {
            }
            "GAC" -> {
            }
            "Haval" -> {
            }
            "Hyundai" -> {
            }
            "Lada(ВАЗ)" -> {
            }
            "ГАЗ" -> {
            }
            "Зил" -> {
            }
            "Москвич" -> {
            }
            "Isuzu" -> {
            }
            "Mercedes-Benz" -> {
            }
            "Уаз" -> {
            }
            "Howo" -> {
            }
            "MAN" -> {
            }
            "KIA" -> {
            }
            "Маз" -> {
            }
            "Changan" -> {
            }
            "Dongfeng" -> {
            }
            "Shacman" -> {
            }
            "Foton" -> {
            }
            "Geely" -> {
            }
            "Genesis" -> {
            }
            "Hafei" -> {
            }
            "Honda" -> {
            }
            "Hummer" -> {
            }
            "Infiniti" -> {
            }
            "JAC" -> {
            }
            "Jaguar" -> {
            }
            "Jeep" -> {
            }
            "King Long" -> {
            }
            "Land Rover" -> {
            }
            "Lexus" -> {
            }
            "Lifan" -> {
            }
            "Lincoln" -> {
            }
            "Maybach" -> {
            }
            "Mazda" -> {
            }
            "Mini" -> {
            }
            "Mitsubishi" -> {
            }
            "Nio" -> {
            }
            "Nissan" -> {
            }
            "Opel" -> {
            }
            "Peugeot" -> {
            }
            "Porsche" -> {
            }
            "Ravon" -> {
            }
            "Renault" -> {
            }
            "Samsung" -> {
            }
            "Rolls-Royce" -> {
            }
            "Rover" -> {
            }
            "Saab" -> {
            }
            "Shuanghuan" -> {
            }
            "Skoda" -> {
            }
            "Smart" -> {
            }
            "SsangYong" -> {
            }
            "Suzuki" -> {
            }
            "Tatra" -> {
            }
            "Tesla" -> {
            }
            "Tofas" -> {
            }
            "Toyota" -> {
            }
            "Volkswagen" -> {
            }
            "Volvo" -> {
            }
            "Wuling" -> {
            }
            "Заз" -> {
            }
            "Иж" -> {
            }
            "Луаз" -> {
            }
            "Раф" -> {
            }
            "DAF" -> {
            }
            "GMC" -> {
            }
            "Shaanxi" -> {
            }
            "Sinotruk" -> {
            }
            "Урал" -> {
            }
            "Alfa-Romeo" -> {
            }
            "Chana" -> {
            }
            "Chrysler" -> {
            }
            "Chery" -> {
            }
            "Citreon" -> {
            }
            "Dodge" -> {
            }
            "Fiat" -> {
            }
            "Force" -> {
            }
        }

        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            resources.getStringArray(arrayRes!!).sortedBy { it }
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.addCarFragmentSpinnerCarModels.adapter = adapter
            binding.addCarFragmentContainerCarModel.visibility = View.VISIBLE
            binding.addCarFragmentSpinnerCarModels.visibility = View.VISIBLE
            binding.addCarFragmentSpinnerCarModels.onItemSelectedListener = this

            binding.addCarFragmentEditTextCarModels.setText("")
        }
    }

}

