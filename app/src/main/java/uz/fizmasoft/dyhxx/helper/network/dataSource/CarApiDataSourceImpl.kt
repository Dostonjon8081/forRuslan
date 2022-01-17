package uz.fizmasoft.dyhxx.helper.network.dataSource

import retrofit2.Response
import uz.fizmasoft.dyhxx.helper.network.CarApiService
import uz.fizmasoft.dyhxx.helper.network.model.*
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModelResponse
import uz.fizmasoft.dyhxx.violation.ViolationPDFModel
import uz.fizmasoft.dyhxx.violation.ViolationPDFResponseModel
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

    override suspend fun getPdfFile(violationPDFModel: ViolationPDFModel): Response<ViolationPDFResponseModel> {
        return carApiService.getPdfFile(violationPDFModel)
    }


}