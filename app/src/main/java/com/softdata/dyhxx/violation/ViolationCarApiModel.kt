package com.softdata.dyhxx.violation

import com.google.gson.annotations.SerializedName

data class ViolationCarApiModel(
    @SerializedName("user_id") val user_id: String,
    @SerializedName("passport") val passport: String,
    @SerializedName("texPassport") val texPassport: String,
)

data class ViolationCarApiModelResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: List<ViolationCarApiModelResponseData>,
    @SerializedName("count") val count: Int,
    @SerializedName("hasCar") val hasCar: String,
    @SerializedName("limit") val limit: String
)

class ViolationCarApiModelResponseData(
    @SerializedName("id") val id: String,
    @SerializedName("drb") val drb: String,
    @SerializedName("qarorSery") val qarorSery: String,
    @SerializedName("qarorNumber") val qarorNumber: String,
    @SerializedName("violationTime") val violationTime: String,
    @SerializedName("violationType") val violationType: String,
    @SerializedName("sum") val sum: String,
    @SerializedName("location") val location: String,
)
