package com.softdata.dyhxx.db

import androidx.room.Database

@Database(entities = [CarEntity::class], version = 1, exportSchema = false)
abstract class CarDataBase {
    abstract fun dao(): CarDao

    companion object {
        const val DB_NAME = "car_db"
    }
}