package uz.fizmasoft.dyhxx.core_fragment.home.add_car

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.core_fragment.home.SpinnerItemClick
import uz.fizmasoft.dyhxx.databinding.FragmentAddCarBinding
import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.util.*

@AndroidEntryPoint
class AddCarFragment : BaseFragment<FragmentAddCarBinding>(FragmentAddCarBinding::inflate),
    SpinnerItemClick {

    private val viewModel: AddCarViewModel by activityViewModels()

    private val args: AddCarFragmentArgs by navArgs()

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

        setupSpinner()
        setup()

        binding.apply {
            addCarFragmentButtonCancel.setOnClickListener { clickButton(binding.addCarFragmentButtonCancel.id) }
            addCarFragmentButtonSave.setOnClickListener { clickButton(binding.addCarFragmentButtonSave.id) }
            addCarFragmentArrowBack.setOnClickListener { clickButton(binding.addCarFragmentArrowBack.id) }
        }
    }

    private fun setup() {
//        val carArgs = args.carArgs
//        if (carArgs != null) {
//            with(binding) {
//                addCarFragmentEtCarNumber.setText(carArgs.carNumber)
//                addCarFragmentTexPassSeries.setText(carArgs.texPass.substring(0, 3))
//                addCarFragmentTexPassNumber.setText(carArgs.texPass.substring(3))
//            }
//        }
    }

    private fun setupSpinner() {
        val mutableListMarks = mutableListOf<String>()
        mutableListMarks.apply {
            addAll(resources.getStringArray(R.array.car_marks))
            sort()
            add(getString(R.string.other))
        }

        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            mutableListMarks
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.addCarFragmentSpinnerCarMarks.adapter = adapter
            binding.addCarFragmentSpinnerCarMarks.onItemSelectedListener = this
        }
    }

    private fun clickButton(id: Int) {
        getBaseActivity {
            when (id) {
                R.id.add_car_fragment_button_cancel -> it.onBackPressed()
                R.id.add_car_fragment_button_save -> saveCar()
                R.id.add_car_fragment_arrow_back -> it.onBackPressed()
            }
        }
    }

    private fun saveCar() {
        binding.wp7progressBar.show()
        val carNumber = binding.addCarFragmentEtCarNumber.text.toString().uppercase()
        val carTexPasSeries = binding.addCarFragmentTexPassSeries.text.toString().uppercase()
        val carTexPasNumber = binding.addCarFragmentTexPassNumber.text.toString().trim()

        carModel = if (!binding.addCarFragmentEditTextCarModels.text.isNullOrEmpty()) {
            binding.addCarFragmentEditTextCarModels.text.toString()
        } else ""

        if (
            carNumber.length >= 8
            && carTexPasNumber.length == 7
            && carTexPasSeries.length >= 3
            && carMark.isNotEmpty()
        ) {

            if (isOnline(requireContext())) {

                viewModel.saveCarApi(
                    requireActivity(),
                    carNumber,
                    carTexPasSeries + carTexPasNumber, carMark, carModel
                )

                viewModel.responseSaveCarApi.observe(viewLifecycleOwner, EventObserver {

                    when (it) {
                        is NetworkResult.Loading -> {
                            binding.wp7progressBar.show()
                        }
                        is NetworkResult.Success -> {
                            binding.wp7progressBar.hide()
                            when (it.data?.message) {
                                "OK" -> {
                                    getBaseActivity { activity ->
                                        activity.navController?.popBackStack()
                                    }
                                }
                                "Car already exists" -> {
                                    carToast(
                                        requireContext(),
                                        getString(R.string.car_exist)
                                    )
                                }
                                "Car not found" -> carToast(
                                    requireContext(),
                                    getString(R.string.car_not_found)
                                )

                                "Bad request" -> carToast(
                                    requireContext(),
                                    getString(R.string.bad_request)
                                )
                                else -> carToast(requireContext(), getString(R.string.bug_server))
                            }

                        }
                        is NetworkResult.Error -> {
                            binding.wp7progressBar.hide()
                        }
                    }
                })


            } else {
                carToast(requireContext(), getString(R.string.not_ethernet))
            }
        } else {
            carToast(requireContext(), getString(R.string.wrong_lines))
        }
//        toast?.show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent?.id == R.id.add_car_fragment_spinner_car_marks) {
            carMark = parent.selectedItem.toString()
            if (carMark.isNotEmpty()) {
                setupModelSpinner()
            }
        } else {
            carModel = parent?.selectedItem.toString()
        }
    }

    private fun setupModelSpinner() {
        binding.addCarFragmentContainerCarModel.visibility = View.VISIBLE

        val arrayRes = when (carMark) {
            "Daewoo" -> R.array.Daewoo
            "Chevrolet" -> R.array.Chevrolet
            "Audi" -> R.array.Audi
            "Baic" -> R.array.Baic
            "BMW" -> R.array.BMW
            "Fiat" -> R.array.Fiat
            "Ford" -> R.array.Ford
            "Hyundai" -> R.array.Hyundai
            "Lada(ВАЗ)" -> R.array.Lada
            "ГАЗ" -> R.array.ГАЗ
            "Москвич" -> R.array.Москвич
            "Isuzu" -> R.array.Isuzu
            "Mercedes-Benz" -> R.array.Mercedes
            "Уаз" -> R.array.Уаз
            "KIA" -> R.array.KIA
            "Infiniti" -> R.array.Infiniti
            "Lexus" -> R.array.Lexus
            "Mazda" -> R.array.Mazda
            "Mitsubishi" -> R.array.Mitsubishi
            "Nissan" -> R.array.Nissan
            "Opel" -> R.array.Opel
            "Peugeot" -> R.array.Peugeot
            "Porsche" -> R.array.Porsche
            "Tesla" -> R.array.Tesla
            "Toyota" -> R.array.Toyota
            "Volkswagen" -> R.array.Volkswagen
            "Заз" -> R.array.Заз
            else -> null
        }

        if (arrayRes != null) {

            val arrayModels = resources.getStringArray(arrayRes)
            val mutableListModels = mutableListOf<String>()
            mutableListModels.addAll(arrayModels)
            mutableListModels.sort()
            mutableListModels.add(getString(R.string.other))

            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                mutableListModels
            ).also { adapter ->

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                binding.addCarFragmentSpinnerCarModels.visibility = View.VISIBLE
                binding.addCarFragmentEditTextCarModels.visibility = View.GONE

                binding.addCarFragmentSpinnerCarModels.adapter = adapter
                binding.addCarFragmentSpinnerCarModels.onItemSelectedListener = this

                binding.addCarFragmentEditTextCarModels.setText("")
            }
        } else {

            binding.addCarFragmentSpinnerCarModels.visibility = View.GONE
            binding.addCarFragmentEditTextCarModels.visibility = View.VISIBLE
        }
    }


}


