package com.yuriysurzhikov.autobroker.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yuriysurzhikov.autobroker.model.entity.UserLocation

@Entity(tableName = "usr")
data class UserRoom(
    @PrimaryKey(autoGenerate = false)
    var userId: String,
    var pin: String,
    var email: String?,
    var phone: String?,
    var displayName: String,
    var photoUrl: String,
    val locationId: Long
)