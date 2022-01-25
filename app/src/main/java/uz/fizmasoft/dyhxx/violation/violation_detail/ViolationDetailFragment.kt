package uz.fizmasoft.dyhxx.violation.violation_detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentViolationDetailBinding
import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.util.EventObserver
import uz.fizmasoft.dyhxx.helper.util.TELEGRAM_AUTH_URL
import uz.fizmasoft.dyhxx.helper.util.logd
import uz.fizmasoft.dyhxx.violation.ViolationCarModel

@AndroidEntryPoint
class ViolationDetailFragment :
    BaseFragment<FragmentViolationDetailBinding>(FragmentViolationDetailBinding::inflate) {

    private val arg: ViolationDetailFragmentArgs by navArgs()
    private val viewModel: ViolationDetailViewModel by activityViewModels()
    private var argModel: ViolationCarModel? = null
    private lateinit var pdfResponseModel: ViolationPDFResponseModel

    private var clickUrl = ""
    private var uPayUrl = ""
    private var payMe = ""

    private var pdfEventId = ""

    override fun onAttach(context: Context) {
        if (arg.violationDetailArgs != null) {
            argModel = arg.violationDetailArgs!!
            viewModel.violationPay(argModel?.qarorSery + argModel?.qarorNumber)
            if (argModel?.qarorSery != "KV")
                viewModel.violationPDF(argModel?.id ?: 0)
        }

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                getBaseActivity {
                    it.navController?.popBackStack()
                }
            }
        })
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor: View = requireActivity().window.decorView
            requireActivity().window.statusBarColor =
                resources.getColor(R.color.violation_detail_light_blue_color, null)
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }

        binding.violationDetailFragmentArrowBack.setOnClickListener { requireActivity().onBackPressed() }

        loadPayLink()

        initData()

        initItems()

    }

    private fun initItems() {
        binding.apply {
            violationDetailFragmentPayClick.setOnClickListener { goToPay(clickUrl) }
            violationDetailFragmentPayPayme.setOnClickListener { goToPay(payMe) }
            violationDetailFragmentPayUpay.setOnClickListener { goToPay(uPayUrl) }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initData() {


        binding.apply {

            if (argModel != null) {

//                violationDetailFragmentTitle.text = argModel?.
                violationDetailFragmentTypeText.text = argModel?.violationType
                violationFragmentDetailSum.text = getSum(argModel?.sum ?: 0L)
                violationFragmentDetailTime.text =
                    cutViolationDate(argModel?.violationTime!!.split("T")[0])
                violationFragmentDetailTimeHour.text =
                    cutViolationHour(argModel?.violationTime!!.split("T")[1])

                when (argModel!!.qarorSery) {
                    "RR" -> protocolRR()

                    "KV" -> protocolKV()

                    else -> protocolEverythingElse()
                }
            }
        }
    }

    private fun protocolEverythingElse() {

        viewModel.responseViolationPdf.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is NetworkResult.Success -> {
//                    pdfEventId = it.data?.eventId ?: ""
                }
            }
        })
        controlMediaVisibility(true)
        binding.apply {
            violationDetailFragmentPdf.setOnClickListener { }
            violationDetailFragmentTypeImage
                .setImageDrawable(resources.getDrawable(R.drawable.ic_radar, null))
        }
    }

    private fun protocolKV() {
        controlMediaVisibility()
        binding.apply {
            violationDetailFragmentTypeImage
                .setImageDrawable(resources.getDrawable(R.drawable.ic_policeman, null))
        }
    }

    private fun protocolRR() {
        getBaseActivity { base ->
            binding.violationDetailFragmentLocationContainer.setOnClickListener { _ ->
                base.navController?.navigate(
                    ViolationDetailFragmentDirections
                        .actionViolationDetailFragmentToViolationMapsFragment(
                            pdfEventId
                        )
                )
            }
            binding.violationDetailFragmentVideoContainer.setOnClickListener {
                base.navController?.navigate(ViolationDetailFragmentDirections.actionViolationDetailFragmentToViolationVideoFragment(pdfEventId))
            }
        }


        controlMediaVisibility(true, true, true)
        viewModel.responseViolationPdf.observe(viewLifecycleOwner, EventObserver {

            when (it) {
                is NetworkResult.Success -> {
                    pdfEventId = it.data?.eventId ?: ""
                    /* PDFUtils.createPdf(
                         requireActivity(),
                         requireActivity().filesDir.absolutePath,
                         it.data!!.file.pdfData,
                         argModel?.id.toString() ?: ""
                     )
                      PDFUtils.openPDF(
                          requireActivity(),
                          requireActivity().filesDir.absolutePath,
                          argModel?.id.toString() ?: ""
  //                        violationPDFQaror?.qaror ?: ""
                      )*/
                }
            }
        })
        binding.violationDetailFragmentTypeImage
            .setImageDrawable(resources.getDrawable(R.drawable.ic_artist, null))

    }

    private fun loadPayLink() {
        viewModel.responseViolationPay.observe(viewLifecycleOwner, EventObserver {

            when (it) {
                is NetworkResult.Loading -> {
                }
                is NetworkResult.Success -> {
                    binding.violationDetailFragmentDiscountSum.text =
                        it.data?.check?.amount.toString()
                    if (it.data != null) {
                        clickUrl = it.data.click
                        payMe = it.data.payme
                        uPayUrl = it.data.uPay
                    }
//                    logd("loadPayLink")
                }
                is NetworkResult.Error -> {
                }
            }

        })
    }

    private fun getSum(sum: Long): String {
        var suma = sum
        val sumStringList = mutableListOf<String>()
        val sumString = StringBuilder()

        while (suma > 1000) {
            sumStringList.add(
                (if ((suma % 1000) == 0L) "000"
                else suma % 1000).toString()
            )
            sumStringList.add(" ")
            suma /= 1000
        }
        sumStringList.add(suma.toString())

        for (item in sumStringList.size - 1 downTo 0) {
            sumString.append(sumStringList[item])
        }

        sumString.append(",00 ")
//            sumString.append(binding.root.context.getString(R.string.sum))
        return sumString.toString()
    }

    private fun cutViolationHour(hour: String): String {
        val splitHour = hour.split(":")
        return "${splitHour[0]}:${splitHour[1]}:00"
    }

    private fun cutViolationDate(date: String): String {
        val splitDate = date.split("-")
        return "${splitDate[2]}.${splitDate[1]}.${splitDate[0]}"
    }

    private fun controlMediaVisibility(
        pdfVisibility: Boolean = false,
        locationVisibility: Boolean = false,
        videoVisibility: Boolean = false
    ) {
        binding.apply {
            violationDetailFragmentPdf.visibility =
                if (pdfVisibility) VISIBLE else GONE

            violationDetailFragmentLocationContainer.visibility =
                if (locationVisibility) VISIBLE else GONE

            violationDetailFragmentVideoContainer.visibility =
                if (videoVisibility) VISIBLE else GONE
        }
    }

    private fun goToPay(url:String){

        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
//        requireActivity().startActivity(Intent(url))
    }
}