package com.softdata.dyhxx.helper.network.model

import com.google.gson.annotations.SerializedName

data class AllCars(
    @SerializedName("id") val id: String
)

data class AllCarsResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: List<AllCarsData>
)

class AllCarsData(
    @SerializedName("id") val id: String,
    @SerializedName("user_id") val user_id: String,
    @SerializedName("passport") val passport: String,
    @SerializedName("created_date") val created_date: String,
    @SerializedName("tex_passport") val tex_passport: String
)
