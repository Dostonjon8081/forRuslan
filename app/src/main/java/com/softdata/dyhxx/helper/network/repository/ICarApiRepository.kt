package com.softdata.dyhxx.helper.network.repository

import com.softdata.dyhxx.helper.network.NetworkResult
import com.softdata.dyhxx.helper.network.model.CheckLimitModel
import com.softdata.dyhxx.helper.network.model.CheckLimitModelResponse
import com.softdata.dyhxx.helper.network.model.UserAuthIDModel
import kotlinx.coroutines.flow.Flow

interface ICarApiRepository {
    suspend fun getUserId(token:String): Flow<NetworkResult<UserAuthIDModel>>
    suspend fun checkLimit(checkLimitModel: CheckLimitModel): Flow<NetworkResult<CheckLimitModelResponse>>
}