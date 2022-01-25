package uz.fizmasoft.dyhxx.violation.violation_detail.video

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.network.repository.ICarApiRepository
import uz.fizmasoft.dyhxx.helper.util.Event
import uz.fizmasoft.dyhxx.helper.util.logd
import javax.inject.Inject

@HiltViewModel
class ViolationVideoViewModel @Inject constructor(
    application: Application,
    private val apiRepository: ICarApiRepository
) : AndroidViewModel(application) {

    private val _responseViolationVideo: MutableLiveData<Event<NetworkResult<ViolationVideoApiModel>>> =
        MutableLiveData()
    val responseViolationVideo: LiveData<Event<NetworkResult<ViolationVideoApiModel>>> =
        _responseViolationVideo

    fun violationVideo(eventId: String) = viewModelScope.launch {
        apiRepository.violationVideo(eventId).collect { value ->
            _responseViolationVideo.postValue(Event(value))
        }
    }

}