package uz.fizmasoft.dyhxx.helper.network.repository

import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.network.model.*
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModel
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModelResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import uz.fizmasoft.dyhxx.violation.ViolationPDFModel
import uz.fizmasoft.dyhxx.violation.ViolationPDFResponseModel

interface ICarApiRepository {
    suspend fun getUserId(token: String): Flow<NetworkResult<UserAuthIDModel>>
    suspend fun checkLimit(checkLimitModel: CheckLimitModel): Flow<NetworkResult<CheckLimitModelResponse>>
    suspend fun saveCar(saveCarModel: SaveCarModel): Flow<NetworkResult<SaveCarResponse>>
    suspend fun allCars(allCars: AllCars): Flow<NetworkResult<AllCarsResponse>>
    suspend fun removeCar(removeCarModel: RemoveCarModel): Flow<NetworkResult<RemoveCarModelResponse>>
    suspend fun getViolation(model: ViolationCarApiModel): Flow<NetworkResult<ViolationCarApiModelResponse>>
    suspend fun getPdfFile(violationPDFModel: ViolationPDFModel):Flow<NetworkResult<ViolationPDFResponseModel>>
}