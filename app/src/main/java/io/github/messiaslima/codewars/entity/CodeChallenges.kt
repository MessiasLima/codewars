package io.github.messiaslima.codewars.entity

import java.io.Serializable

data class CodeChallenges(
    val totalAuthored: Long? = null,
    val totalCompleted: Long? = null
): Serializable