package com.yuriysurzhikov.autobroker.util

object CarNumberUtils {
    private val REGIONS = setOf(
        "АН",
        "АА",
        "АІ",
        "АВ",
        "АС",
        "АЕ",
        "АК",
        "АМ",
        "АО",
        "АР",
        "АТ",
        "АХ",
        "ВА",
        "ВВ",
        "ВС",
        "ВЕ",
        "ВН",
        "ВІ",
        "ВК",
        "ВМ",
        "ВО",
        "ВР",
        "ВТ",
        "ВХ",
        "СА",
        "СВ",
        "СЕ",
        "СН"
    )

    fun isValidRegion(code: String?): Boolean {
        return code in REGIONS
    }

    fun isValidSerial(code: String?): Boolean {
        return !code.isNullOrEmpty() && code.isLettersOnly()
    }
}