package com.softdata.dyhxx.helper.network.model

import com.google.gson.annotations.SerializedName

data class RemoveCarModel(
    @SerializedName("user_id") val user_id: String,
    @SerializedName("carNumber") val carNumber: String,
)

data class RemoveCarModelResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
)


