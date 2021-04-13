package com.yuriysurzhikov.autobroker.model.local

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithLocation(
    @Embedded
    val location: UserLocationRoom,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "userId"
    )
    val user: UserRoom
)