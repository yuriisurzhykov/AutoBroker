package com.yuriysurzhikov.autobroker.model.entity

data class CarBrand(
    val id: String,
    val name: String,
    val iconUrl: String?,
    val models: List<CarModel>
)