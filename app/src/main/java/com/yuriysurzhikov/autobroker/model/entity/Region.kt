package com.yuriysurzhikov.autobroker.model.entity

data class Region (
    var externalId: String,
    val localizations: List<StringItem> = listOf(),
    val cities: MutableList<City> = mutableListOf()
)