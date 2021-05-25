package com.yuriysurzhikov.autobroker.repository.utils

import com.yuriysurzhikov.autobroker.model.entity.RegionNumber
import java.lang.Exception

object FormatUtils {
    fun castPrice(string: String?): Double {
        return if (string.isNullOrEmpty()) 0.0 else
            try {
                string.toDouble()
            } catch (e: Exception) {
                0.0
            }
    }

    fun castMileage(mileage: String?): Double {
        return if (mileage.isNullOrEmpty()) {
            0.0
        } else try {
            mileage.toDouble()
        } catch (e: Exception) {
            0.0
        }
    }

    @JvmStatic
    fun parsePrice(value: Double): String? {
        return try {
            value.toString()
        } catch (e: Exception) {
            null
        }
    }

    @JvmStatic
    fun formatCarNumber(number: RegionNumber?): String? {
        return if (number == null) null else {
            "${number.regionCode} ${number.number} ${number.serialCode}"
        }
    }
}