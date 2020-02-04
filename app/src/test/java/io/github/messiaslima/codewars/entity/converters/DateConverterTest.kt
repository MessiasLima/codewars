package io.github.messiaslima.codewars.entity.converters

import org.junit.Test

import org.junit.Assert.*
import java.util.*

class DateConverterTest {

    @Test
    fun dateToLong() {
        val converter = DateConverter()
        val nowDate = Date()
        val nowTime = nowDate.time
        assertEquals(converter.dateToLong(nowDate), nowTime)
    }

    @Test
    fun `return null when pass null date`() {
        val converter = DateConverter()
        assertNull(converter.dateToLong(null))
    }

    @Test
    fun longToDate() {
        val converter = DateConverter()
        val nowDate = Date()
        val nowTime = nowDate.time
        assertEquals(converter.longToDate(nowTime), nowDate)
    }

    @Test
    fun `return null when pass time null`() {
        assertNull(DateConverter().longToDate(null))
    }
}