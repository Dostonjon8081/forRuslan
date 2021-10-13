package uz.fizmasoft.dyhxx.helper.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CarEntity::class], version = 1, exportSchema = false)
abstract class CarDataBase : RoomDatabase() {
    abstract fun dao(): CarDao
    }