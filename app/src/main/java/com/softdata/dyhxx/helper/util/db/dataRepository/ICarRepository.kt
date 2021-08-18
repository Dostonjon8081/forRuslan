package com.softdata.dyhxx.helper.util.db.dataRepository

import com.softdata.dyhxx.helper.util.db.CarEntity
import kotlinx.coroutines.flow.Flow

interface ICarRepository {
    suspend fun insertCar(carEntity: CarEntity): Long

    suspend fun deleteCar(id: Long): Int

    fun getCar(id: Long): Flow<CarEntity>

    fun allCar(): Flow<List<CarEntity>>
}