package com.softdata.dyhxx.db

import kotlinx.coroutines.flow.Flow


interface CarDataSource {

    fun insertCar(carEntity: CarEntity): Flow<List<CarEntity>>
}