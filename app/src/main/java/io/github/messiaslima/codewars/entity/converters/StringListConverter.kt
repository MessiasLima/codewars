package io.github.messiaslima.codewars.entity.converters

import androidx.room.TypeConverter

class StringListConverter {

    @TypeConverter
    fun listStringToString(list: List<String>?): String {
        return list?.joinToString(separator = "|") ?: ""
    }

    @TypeConverter
    fun stringToList(string: String): List<String>? {
        return string.split("|")
    }
}