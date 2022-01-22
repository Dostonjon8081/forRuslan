package uz.fizmasoft.dyhxx.violation.violation_detail

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
class ViolationDetailViewModel @Inject constructor(
    application: Application,
    private val apiRepository: ICarApiRepository
) : AndroidViewModel(application) {

    private val _responseViolationPay: MutableLiveData<Event<NetworkResult<ViolationPayModelResponse>>> =
        MutableLiveData()
    val responseViolationPay: LiveData<Event<NetworkResult<ViolationPayModelResponse>>> =
        _responseViolationPay

    fun violationPay(act: String) = viewModelScope.launch {
        apiRepository.violationPay(act).collect { value ->
            _responseViolationPay.postValue(Event(value))
        }
    }


    private val _responseViolationPdf: MutableLiveData<Event<NetworkResult<ViolationPDFResponseModel>>> =
        MutableLiveData()
    val responseViolationPdf: LiveData<Event<NetworkResult<ViolationPDFResponseModel>>> =
        _responseViolationPdf

    fun violationPDF(violationId: Long) = viewModelScope.launch {
        apiRepository.violationPdf(violationId).collect { value ->
            _responseViolationPdf.postValue(Event(value))
        }
    }
}