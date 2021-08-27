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

    @Query("select * from car_entities where _id=:id")
    fun getCar(id: Long): Flow<CarEntity>


    @Query("DELETE FROM car_entities  WHERE car_number=:carNumber")
    suspend fun deleteCar(carNumber:String): Int

    @Query("select * from car_entities")
    fun allCars(): Flow<MutableList<CarEntity>>

}