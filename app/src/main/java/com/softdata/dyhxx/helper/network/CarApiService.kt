package com.softdata.dyhxx.helper.network

import com.softdata.dyhxx.helper.network.model.CheckLimitModel
import com.softdata.dyhxx.helper.network.model.CheckLimitModelResponse
import com.softdata.dyhxx.helper.network.model.UserAuthIDModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CarApiService {

    @POST("user/auth")
    suspend fun getUserID(@Header("token") token: String): Response<UserAuthIDModel>

    @POST("carlist/checklimit")
    suspend fun checkLimit(@Body checkLimitModel: CheckLimitModel): Response<CheckLimitModelResponse>

}
