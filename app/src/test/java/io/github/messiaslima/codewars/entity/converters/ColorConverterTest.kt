package io.github.messiaslima.codewars.entity.converters

import io.github.messiaslima.codewars.entity.Color
import org.junit.Test

import org.junit.Assert.*

class ColorConverterTest {

    @Test
    fun colorToString() {
        val converter = ColorConverter()
        Color.values().forEach {
            assertEquals(converter.colorToString(it), it.value)
        }
    }

    @Test
    fun `colorToString return empty string when color is null`() {
        val converter = ColorConverter()
        assertEquals(converter.colorToString(null), "")
    }

    @Test
    fun stringToColor() {
        val converter = ColorConverter()
        assertEquals(converter.stringToColor("blue"), Color.BLUE)
        assertEquals(converter.stringToColor("yellow"), Color.YELLOW)
    }

    @Test
    fun `stringToColor - null when string is empty`(){
        val converter = ColorConverter()
        assertNull(converter.stringToColor(""))
    }

    @Test
    fun `stringToColor - null when string is null`(){
        val converter = ColorConverter()
        assertNull(converter.stringToColor(null))
    }
}