package com.yuriysurzhikov.autobroker.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usr", primaryKeys = ["strId"])
data class UserRoom(
    @PrimaryKey(autoGenerate = false)
    var strId: String?,
    var pin: String,
    var email: String?,
    var phone: String?,
    var displayName: String?,
    var photoUrl: String?
)