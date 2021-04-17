package com.yuriysurzhikov.autobroker.repository.utils

import androidx.room.TypeConverter
import com.yuriysurzhikov.autobroker.model.entity.StringItem

class StringItemConverters {

    @TypeConverter
    fun convertToString(item: StringItem): String {
        return "${item.id}|${item.label}"
    }

    @TypeConverter
    fun covertFromString(item: String): StringItem {
        val stringItems = item.split("|")
        return StringItem(stringItems[0], stringItems[1])
    }

    @TypeConverter
    fun convertStringItemList(items: List<StringItem>): String {
        return items.joinToString(separator = ",") {
            "${it.id}|${it.label}"
        }
    }

    @TypeConverter
    fun convertToItemList(itemsAsString: String): List<StringItem> {
        val items = itemsAsString.split(",")
        val result = mutableListOf<StringItem>()
        items.forEach {
            val itemString = it.split("|")
            result.add(StringItem(itemString[0], itemString[1]))
        }
        return result
    }
}
