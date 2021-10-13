package uz.fizmasoft.dyhxx.helper.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CheckLimitModelResponse(
    @SerializedName("status")
    @Expose
    private val status: Long,
    @SerializedName("limit")
    @Expose
    private val limit: Boolean
)

data class CheckLimitModel(
    @SerializedName("user_id")
    @Expose
    private val user_id: String,
)