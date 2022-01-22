package uz.fizmasoft.dyhxx.violation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ViolationCarModel(
     val id: Long,
//     val drb: String,
     val qarorSery: String,
     val qarorNumber: String,
     val violationTime: String,
     val violationType: String,
     val sum: Long,
     val location: String,
):Parcelable

