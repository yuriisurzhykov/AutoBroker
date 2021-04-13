package com.yuriysurzhikov.autobroker.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "carBrand")
data class CarBrandRoom(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var name: String
)