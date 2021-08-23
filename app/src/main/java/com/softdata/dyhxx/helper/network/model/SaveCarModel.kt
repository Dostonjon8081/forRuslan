package com.softdata.dyhxx.helper.network.model

import com.google.gson.annotations.SerializedName

data class SaveCarModel(
    @SerializedName("user_id") val user_id: String,
    @SerializedName("carNumber") val carNumber: String,
    @SerializedName("texPassport") val texPassport: String
)


data class SaveCarResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String
)