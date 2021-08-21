package com.softdata.dyhxx.helper.db.dataRepository

import com.softdata.dyhxx.helper.db.CarEntity
import com.softdata.dyhxx.helper.db.dataSource.CarDataSourceImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(val sourceImpl: CarDataSourceImpl) : ICarRepository {



    override suspend fun insertCar(carEntity: CarEntity): Long {
        return sourceImpl.insertCar(carEntity)
    }

    override suspend fun deleteCar(id: Long): Int {
        return sourceImpl.deleteCar(id)
    }

    override  fun getCar(id: Long): Flow<CarEntity> {
        return sourceImpl.getCar(id)
    }

    override fun allCar(): Flow<List<CarEntity>> {
        return sourceImpl.allCar()
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