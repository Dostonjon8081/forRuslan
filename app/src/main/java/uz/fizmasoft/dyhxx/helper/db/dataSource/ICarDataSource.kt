package uz.fizmasoft.dyhxx.helper.db.dataSource

import uz.fizmasoft.dyhxx.helper.db.CarEntity
import kotlinx.coroutines.flow.Flow

interface ICarDataSource {
    suspend fun insertCar(carEntity: CarEntity): Long

    suspend fun deleteCar(carNumber:String): Int

    suspend fun deleteAll()

    fun getCar(id: Long): Flow<CarEntity>

    fun allCar(): Flow<List<CarEntity>>

}