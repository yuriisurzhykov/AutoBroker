package com.yuriysurzhikov.autobroker.repository.local

import androidx.room.*
import com.yuriysurzhikov.autobroker.model.local.CarBrandRoom
import com.yuriysurzhikov.autobroker.model.local.CarModelRoom
import com.yuriysurzhikov.autobroker.model.local.CarWithModelsRoom
import com.yuriysurzhikov.autobroker.repository.core.ICrudRepository

@Dao
interface CarsDao : ICrudRepository<CarBrandRoom> {

    @Query("SELECT * FROM carBrand")
    suspend fun getAllCars(): List<CarBrandRoom>

    @Transaction
    @Query("SELECT * FROM carBrand")
    suspend fun getCarBrandWithModels(): List<CarWithModelsRoom>

    @Query("DELETE FROM carBrand")
    suspend fun clearBrandTable()

    @Query("DELETE FROM carModel")
    suspend fun clearCarModelTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCarModel(model: CarModelRoom)
}