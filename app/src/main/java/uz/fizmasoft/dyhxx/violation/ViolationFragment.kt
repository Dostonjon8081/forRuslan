package uz.fizmasoft.dyhxx.violation

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentViolationBinding
import uz.fizmasoft.dyhxx.helper.db.CarEntity
import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.util.EventObserver
import uz.fizmasoft.dyhxx.helper.util.PREF_USER_ID_KEY
import uz.fizmasoft.dyhxx.helper.util.getPref

class ViolationFragment :
    BaseFragment<FragmentViolationBinding>(FragmentViolationBinding::inflate) {

    private val viewModel: ViolationViewModel by activityViewModels()
    private val args: ViolationFragmentArgs by navArgs()
    private var arg: CarEntity? = null
    private val violationRvAdapter by lazy(LazyThreadSafetyMode.NONE) { ViolationRvAdapter() }

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
        requestData()
    }

    private fun requestData() {
        viewModel.getViolationApi(
            ViolationCarApiModel(
                getPref(requireActivity())
                    .getString(PREF_USER_ID_KEY, "")!!,
                arg!!.carNumber,
                arg!!.texPass
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.wp7progressBar.showProgressBar()
        binding.violationFragmentArrowBack.setOnClickListener { activity?.onBackPressed() }

        binding.violationFragmentSwipeRefresh.setColorSchemeColors(ContextCompat.getColor(requireContext(),R.color.toolbar_color))
        binding.violationFragmentSwipeRefresh.setOnRefreshListener(this::swipeRefresh)
        loadData()

    }

    private fun swipeRefresh() {
        binding.wp7progressBar.showProgressBar()
        binding.violationFragmentRv.visibility = View.GONE
        binding.violationsContainerTotalSum.visibility = View.GONE
        requestData()
        loadData()
        binding.violationFragmentSwipeRefresh.isRefreshing = false
    }

    private fun loadData() {

        binding.violationFragmentTitle.text =
            arg!!.carNumber.substring(0, 2) + " " + arg!!.carNumber.substring(2)

        viewModel.responseViolationApiApi.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                is NetworkResult.Success -> {
                    if (it.data?.status == 200) {
                        initDataToRv(it.data.data)
                        binding.violationFragmentCountFines.text = it.data.data.size.toString()
                        binding.wp7progressBar.hideProgressBar()
                    } else {
                        binding.wp7progressBar.hideProgressBar()
                        binding.violationFragmentContainerNoViolation.visibility = View.VISIBLE
                    }
                }
                is NetworkResult.Error -> {
                    binding.wp7progressBar.hideProgressBar()
                    binding.violationFragmentContainerNoConnect.visibility = View.VISIBLE
                }
                is NetworkResult.Loading -> {
                }
            }
        })
    }

    private fun initDataToRv(data: List<ViolationCarApiModelResponseData>) {

        val list: List<ViolationCarModel> = data.map {
            ViolationCarModel(
                it.id,
                it.drb, it.qarorSery, it.qarorNumber,
                it.violationTime, it.violationType,
                it.sum, it.location
            )
        }

        violationRvAdapter.submitList(list)

        binding.violationFragmentRv.visibility = View.VISIBLE
        binding.violationFragmentRv.layoutManager = LinearLayoutManager(requireContext())
        binding.violationFragmentRv.adapter = violationRvAdapter
        binding.violationsContainerTotalSum.visibility = binding.violationFragmentRv.visibility

        initTotalSum(list)
    }

    private fun initTotalSum(list: List<ViolationCarModel>) {
        var totalSum = 0

        for (item in list) {
            totalSum += item.sum.toInt()
        }
        val totalSumStringList = mutableListOf<String>()
        val totalSumString = StringBuilder()

        while (totalSum > 1000) {
            totalSumStringList.add(
                (if ((totalSum % 1000) == 0) "000"
                else totalSum % 1000).toString()
            )
            totalSumStringList.add(" ")
            totalSum /= 1000
        }
        totalSumStringList.add(totalSum.toString())

        for (item in totalSumStringList.size - 1 downTo 0) {
            totalSumString.append(totalSumStringList[item])
        }

        totalSumString.append(",00 ")
        totalSumString.append(getString(R.string.sum))
        binding.violationTotalSum.text = totalSumString.toString()
    }

}