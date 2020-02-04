package io.github.messiaslima.codewars.entity.converters

import org.junit.Test

import org.junit.Assert.*

class StringListConverterTest {

    private val converter = StringListConverter()

    @Test
    fun listStringToString() {
        val list = listOf("a", "b", "c")
        assertEquals(converter.listStringToString(list), "a|b|c")
    }

    @Test
    fun stringToList() {
        val list = listOf("a", "b", "c")
        assertEquals(converter.stringToList("a|b|c"), list)
    }
}