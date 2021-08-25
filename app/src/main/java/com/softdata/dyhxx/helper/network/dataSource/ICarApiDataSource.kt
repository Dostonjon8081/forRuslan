package com.softdata.dyhxx.helper.network.dataSource

import com.softdata.dyhxx.helper.network.model.*
import retrofit2.Response

interface ICarApiDataSource {

    suspend fun getUserId(token: String): Response<UserAuthIDModel>

    suspend fun checkLimit(checkLimitModel: CheckLimitModel):Response<CheckLimitModelResponse>
    suspend fun saveCar(saveCarModel: SaveCarModel):Response<SaveCarResponse>
    suspend fun allCars(allCars: AllCars):Response<AllCarsResponse>
    suspend fun removeCar(removeCarModel: RemoveCarModel):Response<RemoveCarModelResponse>
}
