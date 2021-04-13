package com.yuriysurzhikov.autobroker.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yuriysurzhikov.autobroker.model.entity.*

@Database(
    version = 1,
    entities = [
        CarModel::class,
        CarBrand::class,
        FuelType::class,
        GearboxType::class,
        CarType::class
    ]
)
abstract class SyncDatabase : RoomDatabase() {
    abstract fun getSyncDao(): SyncEntitiesDao
}