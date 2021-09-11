package com.softdata.dyhxx.helper.network.repository

import com.softdata.dyhxx.helper.network.NetworkResult
import com.softdata.dyhxx.helper.network.model.*
import com.softdata.dyhxx.violation.ViolationCarApiModel
import com.softdata.dyhxx.violation.ViolationCarApiModelResponse
import kotlinx.coroutines.flow.Flow

interface ICarApiRepository {
    suspend fun getUserId(token: String): Flow<NetworkResult<UserAuthIDModel>>
    suspend fun checkLimit(checkLimitModel: CheckLimitModel): Flow<NetworkResult<CheckLimitModelResponse>>
    suspend fun saveCar(saveCarModel: SaveCarModel): Flow<NetworkResult<SaveCarResponse>>
    suspend fun allCars(allCars: AllCars): Flow<NetworkResult<AllCarsResponse>>
    suspend fun removeCar(removeCarModel: RemoveCarModel): Flow<NetworkResult<RemoveCarModelResponse>>
    suspend fun getViolation(model: ViolationCarApiModel): Flow<NetworkResult<ViolationCarApiModelResponse>>
}