package uz.fizmasoft.dyhxx.core_fragment.home.add_car

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.activity.MainActivity
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.core_fragment.home.CarRvAdapter
import uz.fizmasoft.dyhxx.core_fragment.home.SpinnerItemClick
import uz.fizmasoft.dyhxx.databinding.FragmentAddCarBinding
import uz.fizmasoft.dyhxx.helper.db.CarEntity
import uz.fizmasoft.dyhxx.helper.network.model.SaveCarModel
import uz.fizmasoft.dyhxx.helper.util.*

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

        val carNumber = binding.addCarFragmentEtCarNumber.text.toString().uppercase()
        val carTexPasSeries = binding.addCarFragmentTexPassSeries.text.toString().uppercase()
        val carTexPasNumber = binding.addCarFragmentTexPassNumber.text.toString().trim()

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
                    0, carNumber,
                    carTexPasSeries + carTexPasNumber,
                    carMark, carModel
                )

            if (isOnline(requireContext())) {

                viewModel.saveCarApi(
                    SaveCarModel(
                        getPref(requireActivity()).getString(PREF_USER_ID_KEY, "")!!, carNumber,
                        carTexPasSeries + carTexPasNumber
                    )
                )

                viewModel.responseSaveCarApi.observe(viewLifecycleOwner, EventObserver {
                    when (it.data?.status) {
                        200 -> {
                            viewModel.insertCarDB(carEntity)
                            carToast(requireContext(), getString(R.string.save))
                            (activity as MainActivity).onBackPressed()
                            adapter.addCar(carEntity)
                        }
                        401 -> {
                            if (it.data?.message == "Car already exists") carToast(
                                requireContext(),
                                getString(R.string.car_exist)
                            )
                            else carToast(requireContext(), getString(R.string.wrong))
                        }
                        400 -> carToast(requireContext(), getString(R.string.bad_request))
                        500 -> carToast(requireContext(), getString(R.string.bug_server))
                    }

                })


            } else {
                carToast(requireContext(), getString(R.string.not_ethernet))
            }
        } else {
            carToast(requireContext(), getString(R.string.wrong))
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


