package uz.fizmasoft.dyhxx.helper.network.model

import com.google.gson.annotations.SerializedName

data class AllCars(
    val token: String
)

data class AllCarsResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: List<AllCarsData>
)

class AllCarsData(
    @SerializedName("id") val id: String,
    @SerializedName("user_id") val user_id: String,
    @SerializedName("car_number") val car_number: String,
    @SerializedName("tex_passport") val tex_passport: String,
    @SerializedName("created_date") val created_date: String
)

