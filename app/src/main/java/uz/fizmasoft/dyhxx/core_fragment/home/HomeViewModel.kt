package uz.fizmasoft.dyhxx.core_fragment.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.fizmasoft.dyhxx.helper.db.CarEntity
import uz.fizmasoft.dyhxx.helper.db.dataRepository.ICarRepository
import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.network.model.*
import uz.fizmasoft.dyhxx.helper.network.repository.ICarApiRepository
import uz.fizmasoft.dyhxx.helper.util.Event
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val apiRepository: ICarApiRepository,
    private val dbRepository: ICarRepository
) : AndroidViewModel(application) {

    private val _responseAllCarsApi: MutableLiveData<Event<NetworkResult<List<AllCarsResponseModel>>>> =
        MutableLiveData()
    val responseAllCarsApi: LiveData<Event<NetworkResult<List<AllCarsResponseModel>>>> = _responseAllCarsApi
    fun allCarsApi(token: String) = viewModelScope.launch {
        apiRepository.allCars(token).collect { values ->
            _responseAllCarsApi.postValue(Event(values))
        }
    }

    private val _allCarDB = MutableLiveData<MutableList<CarEntity>>()
    val allCarDB: LiveData<MutableList<CarEntity>> = _allCarDB
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


    private val _deleteCarDB = MutableLiveData<String>()
    val deleteCarDB: LiveData<String> = _deleteCarDB
    fun deleteCarDB(carNumber:String) {
        viewModelScope.launch (Dispatchers.IO){
            dbRepository.deleteCar(carNumber)
        }
    }



   /* private val _removeCarDB = MutableLiveData<String>()
    val removeCarDB: LiveData<String> = _removeCarDB
    fun removeCarDB(carNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.deleteCar(carNumber)
        }
    }*/

}