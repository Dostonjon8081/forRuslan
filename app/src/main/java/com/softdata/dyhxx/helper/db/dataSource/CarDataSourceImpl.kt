package com.softdata.dyhxx.helper.db.dataSource

import com.softdata.dyhxx.helper.db.CarDataBase
import com.softdata.dyhxx.helper.db.CarEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CarDataSourceImpl @Inject constructor(val db: CarDataBase) : ICarDataSource {

    val dao = db.dao()

    override suspend fun insertCar(carEntity: CarEntity): Long {
        return dao.insertCar(carEntity)
    }

    override suspend fun deleteCar(id: Int): Int {
        return dao.deleteCar(id)
    }

    override fun getCar(id: Long): Flow<CarEntity> {
        return dao.getCar(id)
    }

    override fun allCar(): Flow<List<CarEntity>> {
        return dao.allCars()
    }
}