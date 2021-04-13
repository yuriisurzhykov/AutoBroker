package com.yuriysurzhikov.autobroker.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "usrLocation"
)
data class UserLocationRoom(
    @PrimaryKey(autoGenerate = true)
    val locationId: Long,
    var city: String?,
    var region: String?
)