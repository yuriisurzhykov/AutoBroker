package com.yuriysurzhikov.autobroker.model.entity

data class FuelType (
    val externalId: String,
    val nameLocalization: List<StringItem> = listOf()
)