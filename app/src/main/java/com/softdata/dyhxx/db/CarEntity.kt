package com.softdata.dyhxx.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "carEntity")
class CarEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0L,
    @ColumnInfo(name = "car_number")
    val carNumber: String,
    @ColumnInfo(name = "tex_pass_series")
    val tex_pass_series: String,
    @ColumnInfo(name = "tex_pss_number")
    val texPassNumber: Int,
    @ColumnInfo(name = "car_mark")
    val carMark: String,
    @ColumnInfo(name = "car_model")
    val carModel: String
)