package uz.fizmasoft.dyhxx.violation.violation_detail.maps

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import uz.fizmasoft.dyhxx.R
import uz.fizmasoft.dyhxx.base.BaseFragment
import uz.fizmasoft.dyhxx.databinding.FragmentViolationMapsBinding
import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.util.EventObserver
import uz.fizmasoft.dyhxx.helper.util.logd


@AndroidEntryPoint
class ViolationMapsFragment :
    BaseFragment<FragmentViolationMapsBinding>(FragmentViolationMapsBinding::inflate),
    OnMapReadyCallback {

    private val viewModel: ViolationMapsViewModel by activityViewModels()
    private val arg: ViolationMapsFragmentArgs by navArgs()

    private lateinit var mMap: GoogleMap
    private var longitude = 0.0
    private var latitude = 0.0

    override fun onAttach(context: Context) {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                getBaseActivity {
                    it.navController?.popBackStack()
                }
            }
        })

        viewModel.violationMap(arg.eventId?:"")
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)



        requireActivity().window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = requireContext().resources.getColor(R.color.transparent)

        }

        viewModel.responseViolationMap.observe(viewLifecycleOwner, EventObserver {

            if (it is NetworkResult.Success) {

                latitude = it.data?.lat ?: 0.0
                longitude = it.data?.lng ?: 0.0
                logd(latitude)
                logd(longitude)
                mMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(latitude, longitude),
                        16f
                    )
                )

                mMap.addMarker(MarkerOptions().position(LatLng(latitude,longitude)))
            }
        })
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
    }
}