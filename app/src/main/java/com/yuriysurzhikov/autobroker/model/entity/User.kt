package com.yuriysurzhikov.autobroker.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usr")
data class User(
    @PrimaryKey
    var name: String?,
    var displayName: String?,
    var phone: String?,
    var email: String?,
    var location: UserLocation
)