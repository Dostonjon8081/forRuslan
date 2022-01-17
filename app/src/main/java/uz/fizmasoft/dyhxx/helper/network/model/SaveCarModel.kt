package uz.fizmasoft.dyhxx.helper.network.model

import com.google.gson.annotations.SerializedName

data class SaveCarModel(
    @SerializedName("car_number") val carNumber: String,
    @SerializedName("document_number") val texPassport: String
)


data class SaveCarResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String
)