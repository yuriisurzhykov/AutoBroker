package com.yuriysurzhikov.autobroker.model.entity

data class GearboxType (
    val externalId: String,
    val nameLocalizations: List<StringItem> = listOf()
)