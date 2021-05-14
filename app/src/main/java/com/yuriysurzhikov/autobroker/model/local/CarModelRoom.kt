package com.yuriysurzhikov.autobroker.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "carModel",
    foreignKeys = [ForeignKey(
        entity = CarBrandRoom::class,
        parentColumns = ["brand_id"],
        childColumns = ["carBrandId"]
    )]
)
data class CarModelRoom(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "model_id")
    var id: String = UUID.randomUUID().toString(),
    var name: String,
    var iconUrl: String?,
    @ColumnInfo(name = "carBrandId")
    var carBrandId: String
)