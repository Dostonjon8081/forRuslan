package uz.fizmasoft.dyhxx.violation.violation_detail.video

import com.google.gson.annotations.SerializedName


data class ViolationVideoApiModel(
    @SerializedName("id")
    val id:String,
    @SerializedName("url")
    val url:String
)