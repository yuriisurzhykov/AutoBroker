package com.yuriysurzhikov.autobroker.model.entity

import androidx.room.Entity
import java.util.*

@Entity(tableName = "carType")
class CarType {
    var id: String? = UUID.randomUUID().toString()
    var name: String? = null
}