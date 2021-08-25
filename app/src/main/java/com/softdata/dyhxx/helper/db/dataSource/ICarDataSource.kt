package com.softdata.dyhxx.helper.db.dataSource

import com.softdata.dyhxx.helper.db.CarEntity
import kotlinx.coroutines.flow.Flow

interface ICarDataSource {
    suspend fun insertCar(carEntity: CarEntity): Long

    suspend fun deleteCar(id: Int): Int

    fun getCar(id: Long): Flow<CarEntity>

    fun allCar(): Flow<List<CarEntity>>
}