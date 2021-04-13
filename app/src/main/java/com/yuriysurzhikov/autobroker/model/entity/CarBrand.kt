package com.yuriysurzhikov.autobroker.model.entity

import androidx.room.Entity
import java.util.*

@Entity(tableName = "car")
class CarBrand {
    var id: String? = UUID.randomUUID().toString()
    var name: String? = null
    var models: List<CarModel>? = null
}