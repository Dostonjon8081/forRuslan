package com.softdata.dyhxx.helper.network.dataSource

import android.util.Log
import com.softdata.dyhxx.helper.network.CarApiService
import com.softdata.dyhxx.helper.network.model.CheckLimitModel
import com.softdata.dyhxx.helper.network.model.CheckLimitModelResponse
import com.softdata.dyhxx.helper.network.model.UserAuthIDModel
import retrofit2.Response
import javax.inject.Inject


class CarApiDataSourceImpl @Inject constructor(private val carApiService: CarApiService) :
    ICarApiDataSource {

    override suspend fun getUserId(token: String): Response<UserAuthIDModel> {
        return carApiService.getUserID(token)
    }

    override suspend fun checkLimit(checkLimitModel: CheckLimitModel): Response<CheckLimitModelResponse> {
        return carApiService.checkLimit(checkLimitModel)
    }


}