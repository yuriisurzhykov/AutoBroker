package com.yuriysurzhikov.autobroker.repository.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yuriysurzhikov.autobroker.model.entity.RegionNumber
import com.yuriysurzhikov.autobroker.model.local.*
import com.yuriysurzhikov.autobroker.repository.local.UserCarDao
import com.yuriysurzhikov.autobroker.repository.local.UserLocalDao
import com.yuriysurzhikov.autobroker.repository.utils.ImagesListConverter
import com.yuriysurzhikov.autobroker.repository.utils.RegionNumberConverter

@Database(
    entities = [
        UserRoom::class,
        UserLocationRoom::class,
        CarBrandRoom::class,
        CarModelRoom::class,
        CarRoom::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(ImagesListConverter::class, RegionNumberConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getUserRepository(): UserLocalDao
    abstract fun getUserCarRepository(): UserCarDao
}