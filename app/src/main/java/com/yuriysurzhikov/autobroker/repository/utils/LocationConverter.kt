package com.yuriysurzhikov.autobroker.repository.utils

import androidx.room.TypeConverter
import com.yuriysurzhikov.autobroker.model.entity.UserLocation
import javax.inject.Inject

class LocationConverter @Inject constructor() {
    @TypeConverter
    fun convertFromLocation(location: UserLocation): String {
        return "${location.city}|${location.regionName}|${location.regionId}"
    }

    @TypeConverter
    fun convertToLocation(string: String): UserLocation {
        val splited = string.split("|")
        return UserLocation(splited[0], splited[1], splited[2])
    }
}