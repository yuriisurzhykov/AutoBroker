package com.yuriysurzhikov.autobroker.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.yuriysurzhikov.autobroker.model.entity.StringItem
import com.yuriysurzhikov.autobroker.repository.utils.StringItemConverters
import java.util.*

@Entity(tableName = "fuelType")
data class FuelTypeCache(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var serverId: String,
    @TypeConverters(StringItemConverters::class)
    var name: List<StringItem>
)