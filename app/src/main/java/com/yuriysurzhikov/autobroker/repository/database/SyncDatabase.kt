package com.yuriysurzhikov.autobroker.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yuriysurzhikov.autobroker.model.local.*
import com.yuriysurzhikov.autobroker.repository.local.CarsDao
import com.yuriysurzhikov.autobroker.repository.local.FuelTypesDao
import com.yuriysurzhikov.autobroker.repository.local.GearBoxTypeDao
import com.yuriysurzhikov.autobroker.repository.local.RegionsDao
import com.yuriysurzhikov.autobroker.repository.utils.StringItemConverters

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
@TypeConverters(StringItemConverters::class)
abstract class SyncDatabase : RoomDatabase() {
    abstract fun getRegionsDao(): RegionsDao
    abstract fun getFuelTypesDao(): FuelTypesDao
    abstract fun getGearBoxTypesDao(): GearBoxTypeDao
    abstract fun getCarsDao(): CarsDao
}