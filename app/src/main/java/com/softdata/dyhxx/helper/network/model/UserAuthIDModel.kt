package com.softdata.dyhxx.helper.network.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

public data class UserAuthIDModel(
    @SerializedName("status")
   private val status: Long,
    @SerializedName("user_id")
    private val user_id: Long
)
