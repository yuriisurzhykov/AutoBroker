package com.yuriysurzhikov.autobroker.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yuriysurzhikov.autobroker.model.local.*
import com.yuriysurzhikov.autobroker.repository.local.UserLocalDao

@Database(
    entities = [
        UserRoom::class,
        UserLocationRoom::class,
        CarBrandRoom::class,
        CarModelRoom::class,
        CarRoom::class
    ],
    version = 5,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getUserRepository(): UserLocalDao
}