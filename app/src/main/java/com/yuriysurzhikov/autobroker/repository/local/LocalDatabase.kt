package com.yuriysurzhikov.autobroker.repository.local

import androidx.room.Database
import com.yuriysurzhikov.autobroker.model.local.UserRoom

@Database(version = 1, entities = [UserRoom::class])
abstract class LocalDatabase {
    abstract fun userRepository(): UserLocalDao
}