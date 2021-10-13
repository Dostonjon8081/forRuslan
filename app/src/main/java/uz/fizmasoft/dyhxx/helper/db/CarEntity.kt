package uz.fizmasoft.dyhxx.helper.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "car_entities")
@Parcelize
data class CarEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0L,
    @ColumnInfo(name = "car_number")
    val carNumber: String,
    @ColumnInfo(name = "tex_pass")
    val texPass: String,
    @ColumnInfo(name = "car_mark")
    val carMark: String = "",
    @ColumnInfo(name = "car_model")
    val carModel: String = ""
) : Parcelable