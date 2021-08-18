package com.softdata.dyhxx.helper.util.db.dataRepository

import com.softdata.dyhxx.helper.util.db.CarDataBase
import com.softdata.dyhxx.helper.util.db.CarEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(val db :CarDataBase) : ICarRepository {



    override suspend fun insertCar(carEntity: CarEntity): Long {
        return db.dao().insertCar(carEntity)
    }

    override suspend fun deleteCar(id: Long): Int {
        return db.dao().deleteCar(id)
    }

    override  fun getCar(id: Long): Flow<CarEntity> {
        return db.dao().getCar(id)
    }

    override fun allCar(): Flow<List<CarEntity>> {
        return db.dao().allCars()
    }
//
//    override suspend fun insertCar(carEntity: CarEntity): Long {
//        return CarDataBase.getDatabase(carApp).dao().insertCar(carEntity)
//    }
//
//    override suspend fun deleteCar(id: Long): Int {
//        return CarDataBase.getDatabase(carApp).dao().deleteCar(id)
//    }
//
//    override suspend fun getCar(id: Long): CarEntity {
//        return CarDataBase.getDatabase(carApp).dao().getCar(id)
//    }
//
//    override suspend fun allCar(): List<CarEntity> {
//        return CarDataBase.getDatabase(carApp).dao().allCars()
//    }
}