package com.yuriysurzhikov.autobroker.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yuriysurzhikov.autobroker.model.local.*

@Database(
    version = 1,
    entities = [
        CarModelRoom::class,
        CarBrandRoom::class,
        FuelTypeCache::class,
        GearboxTypeCache::class,
        CarTypeCache::class
    ],
    exportSchema = false
)
abstract class SyncDatabase : RoomDatabase() {
    abstract fun getSyncDao(): SyncEntitiesDao
}