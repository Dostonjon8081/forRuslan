package uz.fizmasoft.dyhxx.violation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.fizmasoft.dyhxx.helper.db.dataRepository.ICarRepository
import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.network.repository.ICarApiRepository
import uz.fizmasoft.dyhxx.helper.util.Event
import javax.inject.Inject

@HiltViewModel
class ViolationViewModel @Inject constructor(
    application: Application,
    private val apiRepository: ICarApiRepository,
    private val dbRepository: ICarRepository
) : AndroidViewModel(application) {

    private val _responseViolationApiApi: MutableLiveData<Event<NetworkResult<List<ViolationCarApiModelResponse>>>> =
        MutableLiveData()
    val responseViolationApiApi: LiveData<Event<NetworkResult<List<ViolationCarApiModelResponse>>>> =
        _responseViolationApiApi

    fun getViolationApi( carNumber:String,texPass:String) = viewModelScope.launch {
        apiRepository.getViolation(carNumber,texPass).collect { values ->
            _responseViolationApiApi.postValue(Event(values))
        }
    }


    private val _responseViolationPdf: MutableLiveData<Event<NetworkResult<ViolationPDFResponseModel>>> =
        MutableLiveData()
    val responseViolationPDF: LiveData<Event<NetworkResult<ViolationPDFResponseModel>>> =
        _responseViolationPdf

    fun getPdfFile(violationPDFModel: ViolationPDFModel) = viewModelScope.launch {
        apiRepository.getPdfFile(violationPDFModel).collect { value ->
            _responseViolationPdf.postValue(Event(value))
        }
    }


}