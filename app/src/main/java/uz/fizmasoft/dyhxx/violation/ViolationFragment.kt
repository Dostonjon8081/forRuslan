package uz.fizmasoft.dyhxx.violation

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentViolationBinding
import uz.fizmasoft.dyhxx.helper.db.CarEntity
import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.util.*


@AndroidEntryPoint
class ViolationFragment :
    BaseFragment<FragmentViolationBinding>(FragmentViolationBinding::inflate), ClickViolationRv {

    private val viewModel: ViolationViewModel by activityViewModels()
    private val args: ViolationFragmentArgs by navArgs()
    private var arg: CarEntity? = null
    private val violationRvAdapter by lazy(LazyThreadSafetyMode.NONE) { ViolationRvAdapter() }
//    private var violationPDFModel: ViolationPDFModel? = null
//    private var violationPDFQaror: ViolationPDFQaror? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                getBaseActivity {
                    it.navController?.popBackStack()
                }
            }
        })
        arg = args.violationArgs


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor: View = requireActivity().window.decorView
            requireActivity().window.statusBarColor =
                resources.getColor(R.color.app_background_color, null)
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        binding.wp7progressBar.show()
        binding.violationFragmentArrowBack.setOnClickListener { activity?.onBackPressed() }

        binding.violationFragmentSwipeRefresh.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(),
                R.color.dark_blue
            )
        )
        binding.violationFragmentSwipeRefresh.setOnRefreshListener(this::swipeRefresh)
        requestData()
        loadData()

    }

    private fun swipeRefresh() {
        requestData()
    }

    private fun requestData() {

        controlUIVisibility()
        arg?.let {
            viewModel.getViolationApi(
                it.carNumber,
                it.texPass
            )
        }
    }

    private fun loadData() {

        binding.violationFragmentTitle.text =
            arg?.carNumber?.substring(0, 2) + " " + arg?.carNumber?.substring(2)

        viewModel.responseViolationApiApi.observe(viewLifecycleOwner, EventObserver {

            when (it) {
                is NetworkResult.Success -> {

                    if (it.data != null)
                        if (it.data.isNotEmpty()) {
                            initDataToRv(it.data)
                            binding.violationFragmentCountFines.text = it.data?.size.toString()
                            binding.wp7progressBar.hide()
                        } else {
                            binding.wp7progressBar.hide()
                            binding.violationFragmentContainerNoViolation.visibility = View.VISIBLE
                        }
                }
                is NetworkResult.Error -> {
                    binding.wp7progressBar.hide()
                    binding.violationFragmentContainerNoConnect.visibility = View.VISIBLE
                }
                is NetworkResult.Loading -> {
                    binding.wp7progressBar.show()
                }
            }
            binding.violationFragmentSwipeRefresh.isRefreshing = false
        })
    }

    private fun initDataToRv(data: List<ViolationCarApiModelResponse>) {

        val list: List<ViolationCarModel> = data.map {
            ViolationCarModel(
                it.id, it.protocol.series,
                it.protocol.number.toString(), it.time,
                it.type, it.sum, it.location
            )
        }

        violationRvAdapter.submitList(list)
        violationRvAdapter.rvClick(this@ViolationFragment)

        binding.violationFragmentRv.visibility = View.VISIBLE
        binding.violationFragmentRv.layoutManager = LinearLayoutManager(requireContext())
        binding.violationFragmentRv.adapter = violationRvAdapter

    }

    override fun violationFileID(id: String, qaror: String) {

    }

    private fun controlUIVisibility(){
        binding.apply {
            violationFragmentContainerNoConnect.visibility = View.INVISIBLE
            violationFragmentContainerNoViolation.visibility = View.INVISIBLE
            violationFragmentRv.visibility = View.INVISIBLE
        }
    }

    override fun violationDetail(violationCarModel: ViolationCarModel) {
        getBaseActivity {
            it.navController?.navigate(ViolationFragmentDirections.actionViolationFragmentToViolationDetailFragment(violationCarModel))
        }
    }

}

