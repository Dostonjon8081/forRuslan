package com.softdata.dyhxx.core_fragment.add_car

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.softdata.dyhxx.helper.db.CarEntity
import com.softdata.dyhxx.helper.db.dataRepository.ICarRepository
import com.softdata.dyhxx.helper.network.NetworkResult
import com.softdata.dyhxx.helper.network.model.*
import com.softdata.dyhxx.helper.network.repository.ICarApiRepository
import com.softdata.dyhxx.helper.util.logd
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCarViewModel @Inject constructor(
    application: Application,
    private val apiRepository: ICarApiRepository,
    private val dbRepository: ICarRepository
) : AndroidViewModel(application) {

    private val _responseUserIdApi: MutableLiveData<NetworkResult<UserAuthIDModel>> = MutableLiveData()
    val responseUserIdApi: LiveData<NetworkResult<UserAuthIDModel>> = _responseUserIdApi
    fun getUserIdApi(token: String) = viewModelScope.launch {
        apiRepository.getUserId(token).collect { values ->
            _responseUserIdApi.postValue(values)
        }
    }

    private val _responseCheckLimitApi: MutableLiveData<NetworkResult<CheckLimitModelResponse>> =
        MutableLiveData()
    val responseCheckLimitApi: LiveData<NetworkResult<CheckLimitModelResponse>> = _responseCheckLimitApi
    fun checkLimitApi(checkLimitModel: CheckLimitModel) = viewModelScope.launch {
        apiRepository.checkLimit(checkLimitModel).collect { values ->

            _responseCheckLimitApi.postValue(values)
        }
    }

    private val _responseSaveCarApi: MutableLiveData<NetworkResult<SaveCarResponse>> =
        MutableLiveData()
    val responseSaveCarApi: LiveData<NetworkResult<SaveCarResponse>> = _responseSaveCarApi
    fun saveCarApi(saveCarModel: SaveCarModel) = viewModelScope.launch {
        apiRepository.saveCar(saveCarModel).collect { values ->
            _responseSaveCarApi.postValue(values)
        }
    }


    private val _responseAllCarsApi: MutableLiveData<NetworkResult<AllCarsResponse>> =
        MutableLiveData()
    val responseAllCarsApi: LiveData<NetworkResult<AllCarsResponse>> = _responseAllCarsApi
    fun allCarsApi(allCars: AllCars) = viewModelScope.launch {
        apiRepository.allCars(allCars).collect { values ->
            _responseAllCarsApi.postValue(values)
        }
    }


    private val _responseRemoveCarApi: MutableLiveData<NetworkResult<RemoveCarModelResponse>> =
        MutableLiveData()
    val responseRemoveCarApi: LiveData<NetworkResult<RemoveCarModelResponse>> = _responseRemoveCarApi
    fun removeCarApi(removeCarModel: RemoveCarModel) = viewModelScope.launch {
        apiRepository.removeCar(removeCarModel).collect { values ->
            _responseRemoveCarApi.postValue(values)
        }
    }


    private val _allCarDB = MutableLiveData<List<CarEntity>>()
    val allCarDB: LiveData<List<CarEntity>> = _allCarDB
    fun allCarsDB() {
        viewModelScope.launch {
            dbRepository.allCar().collect {
                _allCarDB.postValue(it)
            }
        }
    }

    private val _getCarDB = MutableLiveData<CarEntity>()
    val getCarDB: LiveData<CarEntity> = _getCarDB
    fun getCarDB(id: Long) {
        viewModelScope.launch {

            dbRepository.getCar(id).collect {
                _getCarDB.postValue(it)
            }
        }
    }


    private val _insertCarDB = MutableLiveData<Long>()
    val insertCarDB: LiveData<Long> = _insertCarDB
    fun insertCarDB(carEntity: CarEntity) {
        viewModelScope.launch {
            dbRepository.insertCar(carEntity)
        }
    }


    private val _removeCarDB = MutableLiveData<String>()
    val removeCarDB: LiveData<String> = _removeCarDB
    fun removeCarDB(carNumber:String) {
        viewModelScope.launch {
            dbRepository.deleteCar(carNumber)
//            allCars()
        }
    }
}