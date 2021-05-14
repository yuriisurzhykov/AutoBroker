package com.yuriysurzhikov.autobroker.model.local

import androidx.room.Embedded
import androidx.room.Relation

data class CarWithModelsRoom(
    @Embedded val carBrand: CarBrandRoom,
    @Relation(
        parentColumn = "brand_id",
        entityColumn = "carBrandId"
    )
    val carModels: List<CarModelRoom>
)