package io.github.messiaslima.codewars.entity.converters

import androidx.room.TypeConverter
import io.github.messiaslima.codewars.entity.Color

class ColorConverter {

    @TypeConverter
    fun colorToString(color: Color): String {
        return color.value
    }

    @TypeConverter
    fun stringToColor(string: String): Color? {
        return Color.findByValue(string)
    }
}