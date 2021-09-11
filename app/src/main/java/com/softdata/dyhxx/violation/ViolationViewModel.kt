package com.softdata.dyhxx.violation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.softdata.dyhxx.helper.db.dataRepository.ICarRepository
import com.softdata.dyhxx.helper.network.NetworkResult
import com.softdata.dyhxx.helper.network.repository.ICarApiRepository
import com.softdata.dyhxx.helper.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViolationViewModel @Inject constructor(
    application: Application,
    private val apiRepository: ICarApiRepository,
    private val dbRepository: ICarRepository
) : AndroidViewModel(application) {

    private val _responseViolationApiApi: MutableLiveData<Event<NetworkResult<ViolationCarApiModelResponse>>> =
        MutableLiveData()
    val responseViolationApiApi: LiveData<Event<NetworkResult<ViolationCarApiModelResponse>>> =
        _responseViolationApiApi

    fun getViolationApi(model: ViolationCarApiModel) = viewModelScope.launch {
        apiRepository.getViolation(model).collect { values ->
            _responseViolationApiApi.postValue(Event(values))
        }
    }


}