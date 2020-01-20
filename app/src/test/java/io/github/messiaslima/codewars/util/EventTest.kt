package io.github.messiaslima.codewars.util

import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.*

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

    @Test
    fun eventObserver_trigger_once_per_event() {

        val testString = "Some string"
        val event = Event(testString)

        val mockedUnit = spy(TestObserverUnit::class.java)
        val observer = EventObserver(mockedUnit::unitToBeMocked)

        observer.onChanged(event)
        observer.onChanged(event)

        verify(mockedUnit, times(1)).unitToBeMocked(testString)
    }

    interface TestObserverUnit{
        fun unitToBeMocked(str: String)
    }
}