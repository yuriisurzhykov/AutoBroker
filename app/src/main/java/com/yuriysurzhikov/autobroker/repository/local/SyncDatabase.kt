package com.yuriysurzhikov.autobroker.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yuriysurzhikov.autobroker.model.local.*

@Database(
    entities = [
        CarModelRoom::class,
        CarBrandRoom::class,
        FuelTypeCache::class,
        GearboxTypeCache::class,
        CarTypeCache::class,
        RegionRoom::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SyncDatabase : RoomDatabase() {
    abstract fun getRegionsDao(): RegionsDao
    abstract fun getFuelTypesDao(): FuelTypesDao
    abstract fun getGearBoxTypesDao(): GearBoxTypeDao
}