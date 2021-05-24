package com.yuriysurzhikov.autobroker.repository.utils

import java.lang.Exception

object FormatUtils {
    fun parseCost(string: String?): Double {
        return if (string.isNullOrEmpty()) 0.0 else
            try {
                string.toDouble()
            } catch (e: Exception) {
                0.0
            }
    }

    fun parseMileage(mileage: String?): Double {
        return if (mileage.isNullOrEmpty()) {
            0.0
        } else try {
            mileage.toDouble()
        } catch (e: Exception) {
            0.0
        }
    }
}