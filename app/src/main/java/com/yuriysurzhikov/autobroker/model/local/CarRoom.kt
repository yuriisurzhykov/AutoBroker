package com.yuriysurzhikov.autobroker.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "car")
data class CarRoom(
    @PrimaryKey(autoGenerate = false)
    var id: String = UUID.randomUUID().toString(),
    var name: String,
    var brandId: String,
    var modelId: String,
    var carNumber: String,
    var carYearIssue: String,
    var mileage: Int,
    var description: String,
    var cost: Double
)