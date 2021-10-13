package uz.fizmasoft.dyhxx.helper.db.dataSource

import uz.fizmasoft.dyhxx.helper.db.CarDataBase
import uz.fizmasoft.dyhxx.helper.db.CarEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CarDataSourceImpl @Inject constructor(val db: CarDataBase) : ICarDataSource {

    private val dao = db.dao()

    override suspend fun insertCar(carEntity: CarEntity): Long {
        return dao.insertCar(carEntity)
    }

    override suspend fun deleteCar(carNumber:String): Int {
        return dao.deleteCar(carNumber)
    }

    override suspend fun deleteAll() {
        return dao.deleteAll()
    }

    override fun getCar(id: Long): Flow<CarEntity> {
        return dao.getCar(id)
    }

    override fun allCar(): Flow<MutableList<CarEntity>> {
        return dao.allCars()
    }

}