package io.github.messiaslima.codewars.entity.converters

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun dateToLong(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun longToDate(long: Long?): Date? {
        return long?.let { Date(long) }
    }
}