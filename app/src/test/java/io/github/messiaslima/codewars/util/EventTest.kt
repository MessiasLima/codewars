package io.github.messiaslima.codewars.util

import org.junit.Test

import org.junit.Assert.*

class EventTest {

    @Test
    fun getHasBeenHandled_afterHandle() {
        val event = Event(1)
        event.getContentIfNotHandled()
        assertTrue(event.hasBeenHandled)
    }

    @Test
    fun getHasBeenHandled_beforeHandle() {
        val event = Event(1)
        assertFalse(event.hasBeenHandled)
    }

    @Test
    fun getContentIfNotHandled() {
        val content = Math.random()
        val event = Event(content)
        assertEquals(event.getContentIfNotHandled(), content)
    }

    @Test
    fun getContentIfNotHandled_afterHandled() {
        val content = Math.random()
        val event = Event(content)
        event.getContentIfNotHandled()
        assertNull(event.getContentIfNotHandled())
    }


    @Test
    fun peekContent() {
        val content = Math.random().toString()
        val event = Event(content)
        assertEquals(event.peekContent(), content)
    }
}