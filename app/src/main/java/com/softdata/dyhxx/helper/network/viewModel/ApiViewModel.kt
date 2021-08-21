package com.softdata.dyhxx.helper.network.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.softdata.dyhxx.helper.network.NetworkResult
import com.softdata.dyhxx.helper.network.model.CheckLimitModel
import com.softdata.dyhxx.helper.network.model.CheckLimitModelResponse
import com.softdata.dyhxx.helper.network.model.UserAuthIDModel
import com.softdata.dyhxx.helper.network.repository.ICarApiRepository
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

    fun getUserId(token:String) = viewModelScope.launch {
        repository.getUserId(token).collect { values ->

            _responseUserId.postValue(values)
        }
    }

     private val _responseCheckLimit: MutableLiveData<NetworkResult<CheckLimitModelResponse>> = MutableLiveData()
    val responseCheckLimit: LiveData<NetworkResult<CheckLimitModelResponse>> = _responseCheckLimit

    fun checkLimit(checkLimitModel: CheckLimitModel) = viewModelScope.launch {
        repository.checkLimit(checkLimitModel).collect { values ->

            _responseCheckLimit.postValue(values)
        }
    }


}