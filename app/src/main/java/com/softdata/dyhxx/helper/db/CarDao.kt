package com.softdata.dyhxx.helper.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCar(carEntity: CarEntity): Long

    @Query("select * from car_entity where _id=:id")
    fun getCar(id: Long): Flow<CarEntity>


    @Query("delete from car_entity  where _id=:id")
    suspend fun deleteCar(id: Long): Int

    @Query("select * from car_entity")
    fun allCars(): Flow<List<CarEntity>>



}