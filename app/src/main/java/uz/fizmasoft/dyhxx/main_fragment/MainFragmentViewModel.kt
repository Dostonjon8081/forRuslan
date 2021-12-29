package uz.fizmasoft.dyhxx.main_fragment

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
class MainFragmentViewModel @Inject constructor(
    application: Application,
    private val apiRepository: ICarApiRepository,
    private val dbRepository: ICarRepository
) : AndroidViewModel(application) {

    private val _allCarDB = MutableLiveData<MutableList<CarEntity>>()
    val allCarDB: LiveData<MutableList<CarEntity>> = _allCarDB
    fun allCarsDB() {
        viewModelScope.launch {
            dbRepository.allCar().collect {
                _allCarDB.postValue(it)
            }
        }
    }

}