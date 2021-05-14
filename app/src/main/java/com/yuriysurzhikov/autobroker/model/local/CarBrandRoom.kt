package com.yuriysurzhikov.autobroker.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "carBrand")
data class CarBrandRoom(
    var name: String,
    var iconUrl: String?,
    @PrimaryKey
    @ColumnInfo(name = "brand_id")
    var id: String = UUID.randomUUID().toString()
)