package uz.fizmasoft.dyhxx.core_fragment.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.fizmasoft.dyhxx.databinding.FragmentNotificationBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Notification : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDetach() {
        super.onDetach()
        _binding = null
    }
}