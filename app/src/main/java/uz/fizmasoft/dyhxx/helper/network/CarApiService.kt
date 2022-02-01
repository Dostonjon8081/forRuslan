package uz.fizmasoft.dyhxx.helper.network

import retrofit2.Response
import retrofit2.http.*
import uz.fizmasoft.dyhxx.helper.network.model.AllCarsResponseModel
import uz.fizmasoft.dyhxx.helper.network.model.SaveCarModel
import uz.fizmasoft.dyhxx.violation.ViolationCarApiModelResponse
import uz.fizmasoft.dyhxx.violation.violation_detail.ViolationPDFResponseModel
import uz.fizmasoft.dyhxx.violation.violation_detail.ViolationPayModelResponse
import uz.fizmasoft.dyhxx.violation.violation_detail.maps.ViolationMapApiResponseModel
import uz.fizmasoft.dyhxx.violation.violation_detail.video.ViolationVideoApiModel

interface CarApiService {

//    @POST("user/auth")
//    suspend fun getUserID(@Header("token") token: String): Response<UserAuthIDModel>

//    @POST("carlist/checklimit")
//    suspend fun checkLimit(@Body checkLimitModel: CheckLimitModel): Response<CheckLimitModelResponse>

    @POST("/cars")
    suspend fun saveCar(@Body saveCarModel: SaveCarModel): Response<String>

    @GET("cars/")
    suspend fun allCars(): Response<List<AllCarsResponseModel>>

//    @POST("carlist/remove")
//    suspend fun removeCar(@Body removeCarModel: RemoveCarModel): Response<RemoveCarModelResponse>

    @DELETE("cars/{car_number}")
    suspend fun deleteCar(@Path("car_number") carNumber: String): Response<String>

    @GET("violations/list/{car_number}/{tex_pass}")
    suspend fun getViolation(
        @Path("car_number") carNumber: String,
        @Path("tex_pass") texPass: String
    ): Response<List<ViolationCarApiModelResponse>>

//    @POST("violation/pdf")
//    suspend fun getPdfFile(@Body violationPDFModel: ViolationPDFModel): Response<ViolationPDFResponseModel>

    @GET("violations/pdf/{violationId}")
    @Headers("type:base64")
    suspend fun getViolationPdf(@Path("violationId") violationId:String): Response<ViolationPDFResponseModel>


    @GET("violations/payment/{act}")
    suspend fun violationPay(@Path("act")act: String): Response<ViolationPayModelResponse>


    @GET("violations/location/{eventId}")
    suspend fun violationMap(@Path("eventId") eventId: String): Response<ViolationMapApiResponseModel>

    @GET("violations/video/{eventId}")
    suspend fun violationVideo(@Path("eventId") eventId: String): Response<ViolationVideoApiModel>

}
