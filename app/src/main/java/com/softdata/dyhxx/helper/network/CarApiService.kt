package com.softdata.dyhxx.helper.network

import com.softdata.dyhxx.helper.network.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CarApiService {

    @POST("user/auth")
    suspend fun getUserID(@Header("token") token: String): Response<UserAuthIDModel>

    @POST("carlist/checklimit")
    suspend fun checkLimit(@Body checkLimitModel: CheckLimitModel): Response<CheckLimitModelResponse>

    @POST("carlist/save")
    suspend fun saveCar(@Body saveCarModel: SaveCarModel): Response<SaveCarResponse>

    @POST("carlist/")
    suspend fun allCars(@Body allCars: AllCars): Response<AllCarsResponse>

    @POST("carlist/remove")
    suspend fun removeCar(@Body removeCarModel: RemoveCarModel): Response<RemoveCarModelResponse>

}
