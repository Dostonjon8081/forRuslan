package com.softdata.dyhxx.helper.network

import com.softdata.dyhxx.helper.network.model.*
import com.softdata.dyhxx.violation.ViolationCarApiModel
import com.softdata.dyhxx.violation.ViolationCarApiModelResponse
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

    @POST("violation/list")
    suspend fun getViolation(@Body model: ViolationCarApiModel): Response<ViolationCarApiModelResponse>

}
