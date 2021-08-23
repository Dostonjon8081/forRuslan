package com.softdata.dyhxx.helper.network.repository

import com.softdata.dyhxx.helper.network.NetworkResult
import com.softdata.dyhxx.helper.network.dataSource.ICarApiDataSource
import com.softdata.dyhxx.helper.network.model.*
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
}