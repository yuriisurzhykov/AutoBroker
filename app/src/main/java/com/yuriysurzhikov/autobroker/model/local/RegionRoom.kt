package com.yuriysurzhikov.autobroker.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.yuriysurzhikov.autobroker.model.entity.StringItem
import com.yuriysurzhikov.autobroker.repository.utils.StringItemConverters

@Entity(tableName = "region")
data class RegionRoom(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    var remoteId: String,
    @TypeConverters(StringItemConverters::class)
    var name: List<StringItem>
)