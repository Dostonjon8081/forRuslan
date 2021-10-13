package uz.fizmasoft.dyhxx.helper.network.repository

import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.network.dataSource.ICarApiDataSource
import uz.fizmasoft.dyhxx.helper.network.model.*
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModel
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModelResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CarApiRepositoryImpl @Inject constructor(private val iCarApiDataSource: ICarApiDataSource) :
    BaseApiResponse(), ICarApiRepository {

    override suspend fun getUserId(token: String): Flow<NetworkResult<UserAuthIDModel>> {
        return flow<NetworkResult<UserAuthIDModel>> {
            emit(safeApiCall {
                iCarApiDataSource.getUserId(token)
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun checkLimit(checkLimitModel: CheckLimitModel): Flow<NetworkResult<CheckLimitModelResponse>> {
        return flow {
            emit(safeApiCall {
                iCarApiDataSource.checkLimit(checkLimitModel)
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun saveCar(saveCarModel: SaveCarModel): Flow<NetworkResult<SaveCarResponse>> {
        return flow {
            emit(safeApiCall {
                iCarApiDataSource.saveCar(saveCarModel)
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun allCars(allCars: AllCars): Flow<NetworkResult<AllCarsResponse>> {
        return flow {
            emit(safeApiCall {
                iCarApiDataSource.allCars(allCars)
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun removeCar(removeCarModel: RemoveCarModel): Flow<NetworkResult<RemoveCarModelResponse>> {
        return flow {
            emit(safeApiCall {
                iCarApiDataSource.removeCar(removeCarModel)
            })
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getViolation(model: ViolationCarApiModel): Flow<NetworkResult<ViolationCarApiModelResponse>> {
        return flow {
            emit(safeApiCall {
                iCarApiDataSource.getViolation(model)
            })
        }.flowOn(Dispatchers.IO)
    }
}