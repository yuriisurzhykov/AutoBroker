package com.yuriysurzhikov.autobroker.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "region")
data class RegionRoom(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var remoteId: String,
    var name: String
)