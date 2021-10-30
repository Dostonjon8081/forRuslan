package uz.fizmasoft.dyhxx.core_fragment.home.add_car

import androidx.room.ColumnInfo

class AddCarModel  (
var id: Long = 0L,
@ColumnInfo(name = "car_number")
val carNumber: String,
@ColumnInfo(name = "tex_pass")
val texPass: String,
@ColumnInfo(name = "car_mark")
val carMark: String = "",
@ColumnInfo(name = "car_model")
val carModel: String = "")