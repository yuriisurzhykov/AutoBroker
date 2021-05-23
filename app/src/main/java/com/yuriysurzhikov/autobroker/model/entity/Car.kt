package com.yuriysurzhikov.autobroker.model.entity

import java.util.*

data class Car (
    var id: String = UUID.randomUUID().toString(),
    var name: String,
    var brandId: String,
    var modelId: String,
    var carNumber: String,
    var carYearIssue: String,
    var mileage: Int,
    var description: String,
    var cost: Double
)