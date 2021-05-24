package com.yuriysurzhikov.autobroker.repository.utils

import androidx.room.TypeConverter
import javax.inject.Inject

class ImagesListConverter @Inject constructor() {

    @TypeConverter
    fun convertToString(list: List<String>): String {
        return list.joinToString("|")
    }

    @TypeConverter
    fun convertFromString(string: String): List<String> {
        return string.split("|")
    }
}