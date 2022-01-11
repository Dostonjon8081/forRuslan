package uz.fizmasoft.dyhxx.core_fragment.home.add_car

import android.app.Activity
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
import uz.fizmasoft.dyhxx.helper.util.PREF_USER_ID_KEY
import uz.fizmasoft.dyhxx.helper.util.getPref
import uz.fizmasoft.dyhxx.helper.util.logd
import javax.inject.Inject

@HiltViewModel
class AddCarViewModel @Inject constructor(
    application: Application,
    private val apiRepository: ICarApiRepository,
    private val dbRepository: ICarRepository
) : AndroidViewModel(application) {

    private val _responseSaveCarApi: MutableLiveData<Event<NetworkResult<SaveCarResponse>>> =
        MutableLiveData()
    val responseSaveCarApi: LiveData<Event<NetworkResult<SaveCarResponse>>> = _responseSaveCarApi
    fun saveCarApi(
        activity: Activity,
        carNumber: String,
        texPass: String,
        carMark: String,
        carModel: String
    ) =
        viewModelScope.launch {
            _responseSaveCarApi.postValue(Event(NetworkResult.Loading()))
            getPref(activity).getString(PREF_USER_ID_KEY, "")?.let {
                apiRepository.saveCar(SaveCarModel(it, carNumber, texPass)).collect { values ->
                    if (values.data?.message == "OK") insertCarDB(
                        CarEntity(
                            carNumber = carNumber,
                            texPass = texPass,
                            carMark = carMark,
                            carModel = carModel
                        )
                    )
                    _responseSaveCarApi.postValue(Event(values))
                }
            }
        }


    private val _deleteCarApi = MutableLiveData<Event<NetworkResult<String>>>()
    val deleteCarApi = _deleteCarApi
    fun deleteCarApi(carNumber: String) = viewModelScope.launch {
        _deleteCarApi.postValue(Event(NetworkResult.Loading()))
        apiRepository.deleteCar(carNumber).collect {value->
            if (value.data=="OK")
                deleteCarDB(carNumber)
            _deleteCarApi.postValue(Event(value))
        }
    }

    private val _insertCarDB = MutableLiveData<Long>()
    val insertCarDB: LiveData<Long> = _insertCarDB
    private fun insertCarDB(carEntity: CarEntity) = viewModelScope.launch {
        dbRepository.insertCar(carEntity)
    }

    private val _editCarDB = MutableLiveData<Long>()
    val editCarDB: LiveData<Long> = _insertCarDB
     fun editCarDB(carNumber: String, carMark: String, carModel: String) = viewModelScope.launch {
        dbRepository.editCar(carNumber,carMark,carModel)
    }

    private val _deleteCarDB = MutableLiveData<String>()
    val deleteCarDB: LiveData<String> = _deleteCarDB
    fun deleteCarDB(carNumber:String) {
        viewModelScope.launch (Dispatchers.IO){
            dbRepository.deleteCar(carNumber)
        }
    }


}