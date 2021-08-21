package com.softdata.dyhxx.helper.network.dataSource

import com.softdata.dyhxx.helper.network.model.CheckLimitModel
import com.softdata.dyhxx.helper.network.model.CheckLimitModelResponse
import com.softdata.dyhxx.helper.network.model.UserAuthIDModel
import retrofit2.Response

interface ICarApiDataSource {

    suspend fun getUserId(token: String): Response<UserAuthIDModel>

    suspend fun checkLimit(checkLimitModel: CheckLimitModel):Response<CheckLimitModelResponse>
}
