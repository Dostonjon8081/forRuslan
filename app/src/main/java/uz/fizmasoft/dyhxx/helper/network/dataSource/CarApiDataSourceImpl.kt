package uz.fizmasoft.dyhxx.helper.network.dataSource

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import uz.fizmasoft.dyhxx.helper.network.CarApiService
import uz.fizmasoft.dyhxx.helper.network.model.*
import uz.fizmasoft.dyhxx.helper.util.logd
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModelResponse
import uz.fizmasoft.dyhxx.violation.violation_detail.ViolationPDFResponseModel
import uz.fizmasoft.dyhxx.violation.violation_detail.ViolationPDFResponseModelFile
import uz.fizmasoft.dyhxx.violation.violation_detail.ViolationPayModelResponse
import uz.fizmasoft.dyhxx.violation.violation_detail.maps.ViolationMapApiResponseModel
import uz.fizmasoft.dyhxx.violation.violation_detail.video.ViolationVideoApiModel
import javax.inject.Inject


class CarApiDataSourceImpl @Inject constructor(private val carApiService: CarApiService) :
    ICarApiDataSource {

//    override suspend fun checkLimit(checkLimitModel: CheckLimitModel): Response<CheckLimitModelResponse> {
//        return carApiService.checkLimit(checkLimitModel)
//    }

    override suspend fun saveCar(saveCarModel: SaveCarModel): Response<String> {
        return carApiService.saveCar(saveCarModel)
    }

    override suspend fun allCars(token: String): Response<List<AllCarsResponseModel>> {
        return carApiService.allCars()
    }

    override suspend fun deleteCar(carNumber: String): Response<String> {
        return carApiService.deleteCar(carNumber)
    }

    override suspend fun getViolation(carNumber: String, texPass: String): Response<List<ViolationCarApiModelResponse>> {
                return  carApiService.getViolation(carNumber,texPass)
    }

   /* override suspend fun getPdfFile(violationPDFModel: ViolationPDFModel): Response<ViolationPDFResponseModel> {
        return carApiService.getPdfFile(violationPDFModel)
    }*/

    override suspend fun violationPay(act: String): Response<ViolationPayModelResponse> {
        return carApiService.violationPay(act)
    }

    override suspend fun violationPdf(violationId: Long): Response<ViolationPDFResponseModel> {
        return carApiService.getViolationPdf(violationId.toString())
    }

    override suspend fun violationMap(eventId: String): Response<ViolationMapApiResponseModel> {
        return carApiService.violationMap(eventId)
    }

    override suspend fun violationVideo(eventId: String): Response<ViolationVideoApiModel> {
        return carApiService.violationVideo(eventId)
    }


}