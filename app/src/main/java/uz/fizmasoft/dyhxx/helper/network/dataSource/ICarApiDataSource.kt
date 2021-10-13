package uz.fizmasoft.dyhxx.helper.network.dataSource

import uz.fizmasoft.dyhxx.helper.network.model.*
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModel
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModelResponse
import retrofit2.Response

interface ICarApiDataSource {

    suspend fun getUserId(token: String): Response<UserAuthIDModel>

    suspend fun checkLimit(checkLimitModel: CheckLimitModel):Response<CheckLimitModelResponse>
    suspend fun saveCar(saveCarModel: SaveCarModel):Response<SaveCarResponse>
    suspend fun allCars(allCars: AllCars):Response<AllCarsResponse>
    suspend fun removeCar(removeCarModel: RemoveCarModel):Response<RemoveCarModelResponse>
    suspend fun getViolation(model:ViolationCarApiModel):Response<ViolationCarApiModelResponse>
}
