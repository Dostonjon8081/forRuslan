package uz.fizmasoft.dyhxx.helper.network.repository

import kotlinx.coroutines.flow.Flow
import uz.fizmasoft.dyhxx.helper.network.NetworkResult
import uz.fizmasoft.dyhxx.helper.network.model.AllCarsResponseModel
import uz.fizmasoft.dyhxx.helper.network.model.SaveCarModel
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModelResponse
import uz.fizmasoft.dyhxx.violation.violation_detail.ViolationPDFResponseModel
import uz.fizmasoft.dyhxx.violation.violation_detail.ViolationPayModelResponse
import uz.fizmasoft.dyhxx.violation.violation_detail.maps.ViolationMapApiResponseModel
import uz.fizmasoft.dyhxx.violation.violation_detail.video.ViolationVideoApiModel

interface ICarApiRepository {
    //    suspend fun checkLimit(checkLimitModel: CheckLimitModel): Flow<NetworkResult<CheckLimitModelResponse>>
    suspend fun saveCar(saveCarModel: SaveCarModel): Flow<NetworkResult<String>>
    suspend fun violationPay(act: String): Flow<NetworkResult<ViolationPayModelResponse>>
    suspend fun allCars(token: String): Flow<NetworkResult<List<AllCarsResponseModel>>>

    //    suspend fun removeCar(removeCarModel: RemoveCarModel): Flow<NetworkResult<RemoveCarModelResponse>>
    suspend fun deleteCar(carNumber: String): Flow<NetworkResult<String>>
    suspend fun getViolation(carNumber: String,texPass: String): Flow<NetworkResult<List<ViolationCarApiModelResponse>>>
    suspend fun violationPdf(violationId: Long): Flow<NetworkResult<ViolationPDFResponseModel>>
    suspend fun violationMap(eventId: String): Flow<NetworkResult<ViolationMapApiResponseModel>>
    suspend fun violationVideo(eventId: String): Flow<NetworkResult<ViolationVideoApiModel>>
//    suspend fun getPdfFile(violationPDFModel: ViolationPDFModel):Flow<NetworkResult<ViolationPDFResponseModel>>
}