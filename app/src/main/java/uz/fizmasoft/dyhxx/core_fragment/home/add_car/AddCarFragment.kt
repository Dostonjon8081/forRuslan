package uz.fizmasoft.dyhxx.core_fragment.home.add_car

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.AndroidEntryPoint
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentAddCarBinding
import uz.fizmasoft.dyhxx.helper.db.CarEntity
import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.util.*

@AndroidEntryPoint
class AddCarFragment : BaseFragment<FragmentAddCarBinding>(FragmentAddCarBinding::inflate),
    SpinnerItemClick {

    private val viewModel: AddCarViewModel by activityViewModels()

    private val args: AddCarFragmentArgs by navArgs()

    private lateinit var carArgs: CarEntity

    private var carMark = ""
    private var carModel = ""

    /*override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                getBaseActivity {
                    it.navController?.popBackStack()
                }
            }
        })
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()
        setup()

        binding.apply {
            addCarFragmentButtonCancel.setOnClickListener { clickButton(binding.addCarFragmentButtonCancel.id) }
            addCarFragmentButtonSave.setOnClickListener { clickButton(binding.addCarFragmentButtonSave.id) }
            addCarFragmentArrowBack.setOnClickListener { clickButton(binding.addCarFragmentArrowBack.id) }
            addCarFragmentButtonDelete.setOnClickListener { clickButton(binding.addCarFragmentButtonDelete.id) }
        }
    }

    private fun setup() {

        if (args.carArgs != null) {
            binding.addCarFragmentButtonDelete.visibility = View.VISIBLE
            carArgs = args.carArgs!!
            with(binding) {
                addCarFragmentEtCarNumber.setText(carArgs.carNumber)
                addCarFragmentTexPassSeries.setText(carArgs.texPass.substring(0, 3))
                addCarFragmentTexPassNumber.setText(carArgs.texPass.substring(3))
                addCarFragmentEtCarNumber.isEnabled = false
                addCarFragmentTexPassSeries.isEnabled = false
                addCarFragmentTexPassNumber.isEnabled = false
            }

        }
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
                R.id.add_car_fragment_button_save -> saveOrEditCar()
                R.id.add_car_fragment_arrow_back -> it.onBackPressed()
                R.id.add_car_fragment_button_delete -> alertDeleteCar()
            }
        }
    }

    private fun alertDeleteCar() {
        val builder = AlertDialog.Builder(requireContext())
            .setMessage(R.string.delete_dialog_title)
            .setPositiveButton(R.string.yes) { _, _ ->
                deleteCar(carArgs.carNumber)
            }
            .setNegativeButton(R.string.no) { _, _ ->
            }
        builder.create()
        builder.show()

    }

    private fun deleteCar(carNumber: String) {
        viewModel.deleteCarApi(carNumber)
        viewModel.deleteCarApi.observe(this, EventObserver {

            when (it) {
                is NetworkResult.Error -> {
                    carToast(requireContext(), "Not Success")
                }
                is NetworkResult.Loading -> {
                    carToast(requireContext(), resources.getString(R.string.loading))
                }
                is NetworkResult.Success -> {
                    activity?.onBackPressed()
                }
            }
        })
    }

    private fun saveOrEditCar() {
        if (args.carArgs == null) {
            saveCar()
        } else {
            editCar()
        }
    }

    private fun editCar() {

        val carNumber = binding.addCarFragmentEtCarNumber.text.toString().uppercase().trim()
        if (carMark.isNotEmpty()) {
            if (binding.addCarFragmentEditTextCarModels.isVisible) {
                carModel = binding.addCarFragmentEditTextCarModels.text.toString().trim()
            }

            viewModel.editCarDB(carNumber, carMark, carModel)
            getBaseActivity { activity ->
                activity.navController?.popBackStack()
            }
        }
    }

    private fun saveCar() {

        val carNumber = binding.addCarFragmentEtCarNumber.text.toString().uppercase().trim()
        val carTexPasSeries = binding.addCarFragmentTexPassSeries.text.toString().uppercase().trim()
        val carTexPasNumber = binding.addCarFragmentTexPassNumber.text.toString().trim()

        if (binding.addCarFragmentEditTextCarModels.text.isNullOrEmpty()) {
            carModel = binding.addCarFragmentEditTextCarModels.text.toString()
        }

        if (
            carNumber.length >= 8
            && carTexPasNumber.length == 7
            && carTexPasSeries.length >= 3
            && carMark.isNotEmpty()
        ) {

            if (isOnline(requireContext())) {

                viewModel.saveCarApi(
                    carNumber,
                    carTexPasSeries + carTexPasNumber, carMark, carModel
                )

                viewModel.responseSaveCarApi.observe(viewLifecycleOwner, EventObserver {
                    successCarApiResult(it)
                })

            } else {
                carToast(requireContext(), getString(R.string.not_ethernet))
            }
        } else {
            carToast(requireContext(), getString(R.string.wrong_lines))
        }
    }

    private fun successCarApiResult(data: NetworkResult<String>?) {
        getBaseActivity { activity ->
            when (data) {
                is NetworkResult.Loading ->
                    binding.wp7progressBar.show()

                is NetworkResult.Success -> {
                    binding.wp7progressBar.hide()

                    when (data.data) {
                        "Created" ->
                            activity.navController?.popBackStack()

                        "Conflict" ->
                            carToast(requireContext(), getString(R.string.car_exist))

                        "Not Found" ->
                            carToast(requireContext(), getString(R.string.car_not_found))

                        "Bad request" -> carToast(requireContext(), getString(R.string.bad_request))

                        "Unauthorized" ->
                            carToast(requireContext(), getString(R.string.unauthorized))

                        "Internal server error" ->
                            carToast(requireContext(), getString(R.string.bug_server))

                        "Too Many Requests" -> {
                            carToast(requireContext(), getString(R.string.too_many_requests))
                            activity.navController?.popBackStack()
                        }
                        else -> {

//                                    crashlytics.setCustomKeys {
//                                        key("status", it.data?.status ?: 0)
//                                        key("message", it.data?.message ?: "")
//                                        key("user_id",getPref(requireActivity()).getString(PREF_USER_ID_KEY, "")?:"")
//                                    }
                            crashlytics.recordException(Throwable())

                            carToast(
                                requireContext(),
                                "Xatolik tuzatilmoqda. Iltimos qaytadan kirib urunib ko'ring"
                            )

                            FirebaseCrashlytics.getInstance()
                                .log("message $data" + "status $data")
                        }
                    }
                }
                is NetworkResult.Error ->
                    binding.wp7progressBar.hide()

            }
        }
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


