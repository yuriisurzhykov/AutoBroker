package com.yuriysurzhikov.autobroker.model.entity

import java.util.*

data class Car(
    var id: String = UUID.randomUUID().toString(),
    var name: String,
    var brandId: String,
    var modelId: String,
    var carNumber: RegionNumber,
    var carYearIssue: String,
    var mileage: Double,
    var description: String,
    var cost: Double,
    var images: List<String>
)