package uz.fizmasoft.dyhxx.helper.db.dataRepository

import uz.fizmasoft.dyhxx.helper.db.CarEntity
import uz.fizmasoft.dyhxx.helper.db.dataSource.CarDataSourceImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(private val sourceImpl: CarDataSourceImpl) : ICarRepository {



    override suspend fun insertCar(carEntity: CarEntity): Long {
        return sourceImpl.insertCar(carEntity)
    }

    override suspend fun deleteCar(carNumber:String): Int {
        return sourceImpl.deleteCar(carNumber)
    }

    override suspend fun deleteAll() {
        return sourceImpl.deleteAll()
    }


    override  fun getCar(id: Long): Flow<CarEntity> {
        return sourceImpl.getCar(id)
    }

    override fun allCar(): Flow<MutableList<CarEntity>> {
        return sourceImpl.allCar()
    }

    override suspend fun editCar(carNumber: String, carMark: String, carModel: String) {
        return sourceImpl.editCar(carNumber,carMark,carModel)
    }
}