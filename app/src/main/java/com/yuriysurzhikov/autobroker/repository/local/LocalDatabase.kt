package com.yuriysurzhikov.autobroker.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yuriysurzhikov.autobroker.model.local.UserLocationRoom
import com.yuriysurzhikov.autobroker.model.local.UserRoom

@Database(version = 3, entities = [UserRoom::class, UserLocationRoom::class], exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userRepository(): UserLocalDao
}