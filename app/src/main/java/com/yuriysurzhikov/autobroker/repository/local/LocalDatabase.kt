package com.yuriysurzhikov.autobroker.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yuriysurzhikov.autobroker.model.local.UserRoom

@Database(version = 1, entities = [UserRoom::class])
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userRepository(): UserLocalDao
}