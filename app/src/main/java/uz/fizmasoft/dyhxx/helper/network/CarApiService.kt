package uz.fizmasoft.dyhxx.helper.network

import okhttp3.ResponseBody
import uz.fizmasoft.dyhxx.helper.network.model.*
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModel
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModelResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import uz.fizmasoft.dyhxx.violation.ViolationPDFModel
import uz.fizmasoft.dyhxx.violation.ViolationPDFResponseModel

interface CarApiService {

    @POST("user/auth")
    suspend fun getUserID(@Header("token") token: String): Response<UserAuthIDModel>

    @POST("carlist/checklimit")
    suspend fun checkLimit(@Body checkLimitModel: CheckLimitModel): Response<CheckLimitModelResponse>

    @POST("carlist/save")
    suspend fun saveCar(@Body saveCarModel: SaveCarModel): Response<SaveCarResponse>

    @GET("cars/")
    suspend fun allCars(@Header("authorization") token: String): Response<List<AllCarsData>>

    @POST("carlist/remove")
    suspend fun removeCar(@Body removeCarModel: RemoveCarModel): Response<RemoveCarModelResponse>

    @POST("violation/list")
    suspend fun getViolation(@Body model: ViolationCarApiModel): Response<ViolationCarApiModelResponse>

    @POST("violation/pdf")
    suspend fun getPdfFile(@Body violationPDFModel: ViolationPDFModel): Response<ViolationPDFResponseModel>

}
