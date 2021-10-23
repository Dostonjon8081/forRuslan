package uz.fizmasoft.dyhxx.helper.network.dataSource

import retrofit2.Response
import uz.fizmasoft.dyhxx.helper.network.CarApiService
import uz.fizmasoft.dyhxx.helper.network.model.*
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModel
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModelResponse
import uz.fizmasoft.dyhxx.violation.ViolationPDFModel
import uz.fizmasoft.dyhxx.violation.ViolationPDFResponseModel
import javax.inject.Inject


class CarApiDataSourceImpl @Inject constructor(private val carApiService: CarApiService) :
    ICarApiDataSource {

    override suspend fun getUserId(token: String): Response<UserAuthIDModel> {
        return carApiService.getUserID(token)
    }

    override suspend fun checkLimit(checkLimitModel: CheckLimitModel): Response<CheckLimitModelResponse> {
        return carApiService.checkLimit(checkLimitModel)
    }

    override suspend fun saveCar(saveCarModel: SaveCarModel): Response<SaveCarResponse> {
        return carApiService.saveCar(saveCarModel)
    }

    override suspend fun allCars(allCars: AllCars): Response<AllCarsResponse> {
        return carApiService.allCars(allCars)
    }

    override suspend fun removeCar(removeCarModel: RemoveCarModel): Response<RemoveCarModelResponse> {
        return carApiService.removeCar(removeCarModel)

    }

    override suspend fun getViolation(model: ViolationCarApiModel): Response<ViolationCarApiModelResponse> {
        return carApiService.getViolation(model)
    }

    override suspend fun getPdfFile(violationPDFModel: ViolationPDFModel): Response<ViolationPDFResponseModel> {
        return carApiService.getPdfFile(violationPDFModel)
    }


}