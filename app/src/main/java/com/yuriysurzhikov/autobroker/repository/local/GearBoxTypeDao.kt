package com.yuriysurzhikov.autobroker.repository.local

import androidx.room.*
import com.yuriysurzhikov.autobroker.model.local.GearboxTypeCache

@Dao
abstract class GearBoxTypeDao {

    @Delete
    abstract suspend fun delete(item: GearboxTypeCache)

    @Update
    abstract suspend fun update(item: GearboxTypeCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addAll(items: List<GearboxTypeCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun add(item: GearboxTypeCache)

    @Query("DELETE FROM gearType")
    abstract suspend fun clearGearBoxTypes()

    @Query("SELECT * FROM gearType")
    abstract suspend fun getAll(): List<GearboxTypeCache>
}