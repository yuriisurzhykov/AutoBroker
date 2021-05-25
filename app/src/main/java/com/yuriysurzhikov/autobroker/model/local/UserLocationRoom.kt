package com.yuriysurzhikov.autobroker.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "usrLocation"
)
data class UserLocationRoom(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "locationId")
    val locationId: Long,
    var city: String,
    var regionId: String,
    var regionName: String
)