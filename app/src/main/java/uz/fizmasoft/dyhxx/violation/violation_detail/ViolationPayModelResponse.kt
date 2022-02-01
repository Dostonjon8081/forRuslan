package uz.fizmasoft.dyhxx.violation.violation_detail

import com.google.gson.annotations.SerializedName

data class ViolationPayModelResponse(
    @SerializedName("check")
    val check: ViolationPayModelCheck,
    @SerializedName("Click")
    val click: String,
    @SerializedName("UPay")
    val uPay: String,
    @SerializedName("Payme")
    val payme: String
)

data class ViolationPayModelCheck(
    @SerializedName("amount")
    val amount: Long,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("type")
    val violationType: String
)