package com.yuriysurzhikov.autobroker.repository.local

import androidx.room.*
import com.yuriysurzhikov.autobroker.model.local.GearboxTypeCache
import com.yuriysurzhikov.autobroker.repository.core.ICrudRepository

@Dao
abstract class GearBoxTypeDao :
    ICrudRepository<GearboxTypeCache> {

    @Query("DELETE FROM gearType")
    abstract suspend fun clearGearBoxTypes()

    @Query("SELECT * FROM gearType")
    abstract suspend fun getAll(): List<GearboxTypeCache>
}