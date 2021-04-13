package com.yuriysurzhikov.autobroker.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "carType")
data class CarTypeCache(
    @PrimaryKey(autoGenerate = false)
    var id: String = UUID.randomUUID().toString(),
    var name: String
)