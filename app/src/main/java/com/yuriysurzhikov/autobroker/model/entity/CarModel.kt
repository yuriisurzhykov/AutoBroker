package com.yuriysurzhikov.autobroker.model.entity

import androidx.room.Entity
import java.util.*

@Entity(tableName = "carModel")
class CarModel {
    var id: String? = UUID.randomUUID().toString()
    var name: String? = null
    var carBrand: CarBrand? = null
}