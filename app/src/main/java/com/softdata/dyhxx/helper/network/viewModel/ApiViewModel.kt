package com.softdata.dyhxx.helper.network.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.softdata.dyhxx.helper.network.NetworkResult
import com.softdata.dyhxx.helper.network.model.*
import com.softdata.dyhxx.helper.network.repository.ICarApiRepository
import com.softdata.dyhxx.helper.util.logd
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiViewModel @Inject constructor(
    application: Application,
    private val repository: ICarApiRepository
) : AndroidViewModel(application) {
    private val _responseUserId: MutableLiveData<NetworkResult<UserAuthIDModel>> = MutableLiveData()
    val responseUserId: LiveData<NetworkResult<UserAuthIDModel>> = _responseUserId
    fun getUserId(token: String) = viewModelScope.launch {
        repository.getUserId(token).collect { values ->

            _responseUserId.postValue(values)
        }
    }

    private val _responseCheckLimit: MutableLiveData<NetworkResult<CheckLimitModelResponse>> =
        MutableLiveData()
    val responseCheckLimit: LiveData<NetworkResult<CheckLimitModelResponse>> = _responseCheckLimit
    fun checkLimit(checkLimitModel: CheckLimitModel) = viewModelScope.launch {
        repository.checkLimit(checkLimitModel).collect { values ->

            _responseCheckLimit.postValue(values)
        }
    }

    private val _responseSaveCar: MutableLiveData<NetworkResult<SaveCarResponse>> =
        MutableLiveData()
    val responseSaveCar: LiveData<NetworkResult<SaveCarResponse>> = _responseSaveCar
    fun saveCar(saveCarModel: SaveCarModel) = viewModelScope.launch {
        repository.saveCar(saveCarModel).collect { values ->
            _responseSaveCar.postValue(values)
        }
    }


    private val _responseAllCars: MutableLiveData<NetworkResult<AllCarsResponse>> =
        MutableLiveData()
    val responseAllCars: LiveData<NetworkResult<AllCarsResponse>> = _responseAllCars
    fun allCars(allCars: AllCars) = viewModelScope.launch {
        repository.allCars(allCars).collect { values ->
            _responseAllCars.postValue(values)
        }
    }


    private val _responseRemoveCar: MutableLiveData<NetworkResult<RemoveCarModelResponse>> =
        MutableLiveData()
    val responseRemoveCar: LiveData<NetworkResult<RemoveCarModelResponse>> = _responseRemoveCar
    fun removeCar(removeCarModel: RemoveCarModel) = viewModelScope.launch {
        repository.removeCar(removeCarModel).collect { values ->
            _responseRemoveCar.postValue(values)
        }

    }



}