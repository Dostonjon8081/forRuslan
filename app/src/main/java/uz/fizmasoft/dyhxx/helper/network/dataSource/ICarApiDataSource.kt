package uz.fizmasoft.dyhxx.helper.network.dataSource

import retrofit2.Response
import uz.fizmasoft.dyhxx.helper.network.model.*
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModelResponse
import uz.fizmasoft.dyhxx.violation.ViolationPDFModel
import uz.fizmasoft.dyhxx.violation.ViolationPDFResponseModel

interface ICarApiDataSource {

//    suspend fun checkLimit(checkLimitModel: CheckLimitModel): Response<CheckLimitModelResponse>
    suspend fun saveCar(saveCarModel: SaveCarModel): Response<String>
    suspend fun allCars(token: String): Response<List<AllCarsResponseModel>>
//    suspend fun removeCar(removeCarModel: RemoveCarModel): Response<RemoveCarModelResponse>
    suspend fun deleteCar(carNumber: String): Response<String>
    suspend fun getViolation( carNumber: String, texPass: String): Response<List<ViolationCarApiModelResponse>>
    suspend fun getPdfFile(violationPDFModel: ViolationPDFModel): Response<ViolationPDFResponseModel>
}
