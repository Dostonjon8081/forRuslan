package com.softdata.dyhxx.helper.db.carViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.softdata.dyhxx.helper.db.CarEntity
import com.softdata.dyhxx.helper.db.dataRepository.ICarRepository
import com.softdata.dyhxx.helper.network.NetworkResult
import com.softdata.dyhxx.helper.network.model.AllCars
import com.softdata.dyhxx.helper.network.model.UserAuthIDModel
import com.softdata.dyhxx.helper.util.PREF_TOKEN_KEY
import com.softdata.dyhxx.helper.util.getPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.security.PrivateKey
import javax.inject.Inject

@HiltViewModel
class CarViewModel @Inject constructor(
    application: Application,
    private val repository: ICarRepository
) : AndroidViewModel(application) {

    private val _allCar = MutableLiveData<List<CarEntity>>()
    val allCar: LiveData<List<CarEntity>> = _allCar
    fun allCars() {
        viewModelScope.launch {
            repository.allCar().collect {
                _allCar.postValue(it)
            }
        }
    }

    private val _getCar = MutableLiveData<CarEntity>()
    val getCar: LiveData<CarEntity> = _getCar
    fun getCar(id: Long) {
        viewModelScope.launch {

            repository.getCar(id).collect {
                _getCar.postValue(it)
            }
        }
    }


    private val _insertCar = MutableLiveData<Long>()
    val insertCar: LiveData<Long> = _insertCar
    fun insertCar(carEntity: CarEntity) {
        viewModelScope.launch {
            repository.insertCar(carEntity)
        }
    }


    private val _removeCar = MutableLiveData<Long>()
    val removeCar: LiveData<Long> = _removeCar
    fun removeCar(id: Int) {
        viewModelScope.launch {
            repository.deleteCar(id)
            allCars()
        }
    }

}