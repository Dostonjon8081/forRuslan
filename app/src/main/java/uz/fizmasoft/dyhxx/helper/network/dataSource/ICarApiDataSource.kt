package uz.fizmasoft.dyhxx.helper.network.dataSource

import retrofit2.Response
import uz.fizmasoft.dyhxx.helper.network.model.*
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModel
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModelResponse
import uz.fizmasoft.dyhxx.violation.ViolationPDFModel
import uz.fizmasoft.dyhxx.violation.ViolationPDFResponseModel

interface ICarApiDataSource {

    suspend fun checkLimit(checkLimitModel: CheckLimitModel): Response<CheckLimitModelResponse>
    suspend fun saveCar(saveCarModel: SaveCarModel): Response<SaveCarResponse>
    suspend fun allCars(token: String): Response<List<AllCarsResponseModel>>
    suspend fun removeCar(removeCarModel: RemoveCarModel): Response<RemoveCarModelResponse>
    suspend fun getViolation(model: ViolationCarApiModel): Response<ViolationCarApiModelResponse>
    suspend fun getPdfFile(violationPDFModel: ViolationPDFModel): Response<ViolationPDFResponseModel>
}
