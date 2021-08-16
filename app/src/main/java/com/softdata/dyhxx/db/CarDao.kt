package com.softdata.dyhxx.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCar(carEntity: CarEntity): List<CarEntity>

    @Query("select * from carEntity where _id=:id")
    suspend fun getCar(id: Long): CarEntity


    @Query("delete from carEntity  where _id=:id")
    suspend fun deleteCar(id: Long): Unit

    @Query("select * from carEntity")
    suspend fun allCars(): List<CarEntity>

}