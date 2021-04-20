package com.yuriysurzhikov.autobroker.repository.local

import androidx.room.*
import com.yuriysurzhikov.autobroker.model.local.FuelTypeCache
import com.yuriysurzhikov.autobroker.repository.core.ICrudRepository

@Dao
abstract class FuelTypesDao :
    ICrudRepository<FuelTypeCache> {

    @Query("DELETE FROM fuelType")
    abstract suspend fun clearFuelTypes()

    @Query("SELECT * FROM fuelType")
    abstract suspend fun getAll(): List<FuelTypeCache>
}