package uz.fizmasoft.dyhxx.helper.db.dataRepository

import uz.fizmasoft.dyhxx.helper.db.CarEntity
import kotlinx.coroutines.flow.Flow

interface ICarRepository {
    suspend fun insertCar(carEntity: CarEntity): Long

    suspend fun deleteCar(carNumber:String): Int

    suspend fun deleteAll()

    fun getCar(id: Long): Flow<CarEntity>

    fun allCar(): Flow<MutableList<CarEntity>>
}