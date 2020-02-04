package io.github.messiaslima.codewars.entity.converters

import io.github.messiaslima.codewars.ui.challenges.ChallengeType
import org.junit.Test

import org.junit.Assert.*

class ChallengeTypeConverterTest {

    @Test
    fun challengeTypeToString() {
        val converter = ChallengeTypeConverter()
        assertEquals(converter.challengeTypeToString(ChallengeType.AUTHORED), "AUTHORED")
        assertEquals(converter.challengeTypeToString(ChallengeType.COMPLETED), "COMPLETED")
    }

    @Test
    fun stringToChallengeType() {
        val converter = ChallengeTypeConverter()
        assertEquals(converter.stringToChallengeType("AUTHORED"), ChallengeType.AUTHORED)
        assertEquals(converter.stringToChallengeType("COMPLETED"), ChallengeType.COMPLETED)
    }
}