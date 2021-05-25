package com.yuriysurzhikov.autobroker.model.entity

data class UserLocation(
    var city: String,
    var regionName: String,
    var regionId: String
) {
    companion object {
        fun empty(): UserLocation {
            return UserLocation("", "", "")
        }
    }
}