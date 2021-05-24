package com.yuriysurzhikov.autobroker.repository.utils

import androidx.room.TypeConverter
import com.yuriysurzhikov.autobroker.model.entity.RegionNumber
import java.lang.IllegalStateException
import javax.inject.Inject

class RegionNumberConverter @Inject constructor() {
    @TypeConverter
    fun convertToString(number: RegionNumber): String {
        return "${number.regionCode}|${number.number}|${number.serialCode}"
    }

    @TypeConverter
    fun convertFromString(stringNumber: String): RegionNumber {
        val splited = stringNumber.split("|")
        return try {
            RegionNumber(splited[0], splited[1], splited[2])
        } catch (e: Throwable) {
            throw IllegalStateException("Illegal number of splitted parts of input string! Now it ${splited.size}, should be 3!")
        }
    }
}