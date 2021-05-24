package com.yuriysurzhikov.autobroker.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.yuriysurzhikov.autobroker.model.entity.RegionNumber
import com.yuriysurzhikov.autobroker.repository.utils.ImagesListConverter
import com.yuriysurzhikov.autobroker.repository.utils.RegionNumberConverter
import java.util.*

@Entity(tableName = "car")
data class CarRoom(
    @PrimaryKey(autoGenerate = false)
    var id: String = UUID.randomUUID().toString(),
    var name: String,
    var brandId: String,
    var modelId: String,
    var carYearIssue: String,
    var mileage: Double,
    var description: String,
    var cost: Double,
    @TypeConverters(ImagesListConverter::class)
    var imagesUri: List<String>,
    @TypeConverters(RegionNumberConverter::class)
    var regionNumber: RegionNumber
)