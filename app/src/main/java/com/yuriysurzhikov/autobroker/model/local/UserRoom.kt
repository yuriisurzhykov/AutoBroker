package com.yuriysurzhikov.autobroker.model.local

import androidx.room.*
import com.yuriysurzhikov.autobroker.model.entity.UserLocation

@Entity(tableName = "usr")
data class UserRoom(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "userId")
    var userId: String,
    var password: String,
    var email: String?,
    var phone: String?,
    var displayName: String,
    var photoUrl: String,
    var locationId: Long,
    var fullRegistration: Boolean,
    var isLogged: Boolean
)