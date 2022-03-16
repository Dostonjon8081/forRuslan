package uz.fizmasoft.dyhxx.violation.violation_detail.video

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentViolationVideoBinding
import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.util.EventObserver

@AndroidEntryPoint
class ViolationVideoFragment :
    BaseFragment<FragmentViolationVideoBinding>(FragmentViolationVideoBinding::inflate) {

    private val viewModel: ViolationVideoViewModel by activityViewModels()
    private val args: ViolationVideoFragmentArgs by navArgs()
    private val mediaController by lazy {
        object : MediaController(requireContext()) {
            override fun hide() {
//                binding.violationVideoFragmentProgressBar.hide()
                super.hide()
            }
        }
    }


    override fun onAttach(context: Context) {
        if (args.eventId != null)
            viewModel.violationVideo(args.eventId ?: "")

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
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor: View = requireActivity().window.decorView
            requireActivity().window.statusBarColor =
                resources.getColor(R.color.black, null)
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }


        binding.violationVideoFragmentBackArrow.setOnClickListener {
//            binding.violationVideoFragmentProgressBar.hide()
            requireActivity().onBackPressed()
        }

        viewModel.responseViolationVideo.observe(viewLifecycleOwner, EventObserver {

            binding.apply {
                if (it is NetworkResult.Success) {
                    violationVideoFragmentVideo.setVideoURI(Uri.parse(it.data?.url ?: ""))
                    violationVideoFragmentVideo.start()

                    violationVideoFragmentVideo.setMediaController(mediaController)
                    violationVideoFragmentVideo.setOnInfoListener { _, _, _ ->
                        binding.violationVideoFragmentProgressBar.hide()
                        true
                    }
                }
            }
        })

    }

    override fun onPause() {
        binding.violationVideoFragmentVideo.stopPlayback()
        super.onPause()
    }

}