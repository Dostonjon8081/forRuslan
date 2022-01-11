package uz.fizmasoft.dyhxx.helper.network

import uz.fizmasoft.dyhxx.helper.network.model.*
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModelResponse
import retrofit2.Response
import retrofit2.http.*
import uz.fizmasoft.dyhxx.violation.ViolationPDFModel
import uz.fizmasoft.dyhxx.violation.ViolationPDFResponseModel

interface CarApiService {

//    @POST("user/auth")
//    suspend fun getUserID(@Header("token") token: String): Response<UserAuthIDModel>

//    @POST("carlist/checklimit")
//    suspend fun checkLimit(@Body checkLimitModel: CheckLimitModel): Response<CheckLimitModelResponse>

    @POST("carlist/save")
    suspend fun saveCar(@Body saveCarModel: SaveCarModel): Response<SaveCarResponse>

    @GET("cars/")
    suspend fun allCars(): Response<List<AllCarsResponseModel>>

//    @POST("carlist/remove")
//    suspend fun removeCar(@Body removeCarModel: RemoveCarModel): Response<RemoveCarModelResponse>

    @DELETE("cars/{car_number}")
    suspend fun deleteCar(@Path("car_number") carNumber: String):Response<String>

    @GET("violations/list/{car_number}/{tex_pass}")
    suspend fun getViolation( @Path("car_number") carNumber: String,@Path("tex_pass") texPass: String): Response<List<ViolationCarApiModelResponse>>

    @POST("violation/pdf")
    suspend fun getPdfFile(@Body violationPDFModel: ViolationPDFModel): Response<ViolationPDFResponseModel>

}
