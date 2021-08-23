package com.softdata.dyhxx.helper.network.model

import com.google.gson.annotations.SerializedName

public data class UserAuthIDModel(
    @SerializedName("status") val status: Long,
    @SerializedName("user_id") val user_id: Long
)
