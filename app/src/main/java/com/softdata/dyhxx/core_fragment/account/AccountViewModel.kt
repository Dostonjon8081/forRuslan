package com.softdata.dyhxx.core_fragment.account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.softdata.dyhxx.helper.db.dataRepository.ICarRepository
import com.softdata.dyhxx.helper.network.repository.ICarApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    application: Application,
    private val apiRepository: ICarApiRepository,
    private val dbRepository: ICarRepository
) : AndroidViewModel(application) {

    private val _deleteAll = MutableLiveData<Unit>()
    val deleteAll: LiveData<Unit> = _deleteAll
    fun deleteAll() {
        viewModelScope.launch {
            dbRepository.deleteAll()
        }
    }
}