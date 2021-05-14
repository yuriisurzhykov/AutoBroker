package com.yuriysurzhikov.autobroker.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yuriysurzhikov.autobroker.model.local.CarBrandRoom
import com.yuriysurzhikov.autobroker.model.local.CarModelRoom
import com.yuriysurzhikov.autobroker.model.local.UserLocationRoom
import com.yuriysurzhikov.autobroker.model.local.UserRoom

@Database(
    entities = [
        UserRoom::class,
        UserLocationRoom::class,
        CarBrandRoom::class,
        CarModelRoom::class
    ],
    version = 5,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getUserRepository(): UserLocalDao
    abstract fun getCarsDao(): CarsDao
}