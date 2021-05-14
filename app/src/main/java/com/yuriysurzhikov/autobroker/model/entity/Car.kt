package com.yuriysurzhikov.autobroker.model.entity

data class Car (
    val id: String,
    val name: String,
    val iconUrl: String?,
    val models: List<CarModel>
)