package uz.fizmasoft.dyhxx.violation.violation_detail.maps

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
class ViolationMapsViewModel @Inject constructor(
    application: Application,
    private val apiRepository: ICarApiRepository
) : AndroidViewModel(application) {

    private val _responseViolationMap: MutableLiveData<Event<NetworkResult<ViolationMapApiResponseModel>>> =
        MutableLiveData()
    val responseViolationMap: LiveData<Event<NetworkResult<ViolationMapApiResponseModel>>> =
        _responseViolationMap

    fun violationMap(eventId: String) = viewModelScope.launch {
        apiRepository.violationMap(eventId).collect { value ->
            logd("viewModel: ${value.data}")
            _responseViolationMap.postValue(Event(value))
        }
    }

}